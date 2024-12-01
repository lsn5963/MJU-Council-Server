package depth.mju.council.domain.event.service;

import depth.mju.council.domain.common.FileType;
import depth.mju.council.domain.event.dto.req.CreateEventReq;
import depth.mju.council.domain.event.entity.Event;
import depth.mju.council.domain.event.entity.EventFile;
import depth.mju.council.domain.event.entity.EventGuide;
import depth.mju.council.domain.event.repository.EventFileRepository;
import depth.mju.council.domain.event.repository.EventGuideFileRepository;
import depth.mju.council.domain.event.repository.EventGuideRepository;
import depth.mju.council.domain.event.repository.EventRepository;
import depth.mju.council.domain.notice.dto.req.CreateNoticeReq;
import depth.mju.council.domain.notice.entity.Notice;
import depth.mju.council.domain.notice.entity.NoticeFile;
import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.DefaultAssert;
import depth.mju.council.global.config.UserPrincipal;
import depth.mju.council.infrastructure.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;
    private final EventFileRepository eventFileRepository;
    private final EventGuideRepository eventGuideRepository;
    private final EventGuideFileRepository eventGuideFileRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;

    @Transactional
    public void createEvent(
            UserPrincipal userPrincipal,
            List<MultipartFile> images, CreateEventReq createEventReq)
    {
        UserEntity user = validUserById(userPrincipal.getId());
        validDateRange(createEventReq.getStartDate(), createEventReq.getEndDate());
        Event event = Event.builder()
                .title(createEventReq.getTitle())
                .content(createEventReq.getContent())
                .startDate(createEventReq.getStartDate())
                .endDate(createEventReq.getEndDate())
                .userEntity(user)
                .build();
        eventRepository.save(event);
        uploadEventFiles(images, event, FileType.IMAGE);
    }

    private void uploadEventFiles(List<MultipartFile> files, Event event, FileType fileType) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                saveEventFiles(s3Service.uploadFile(file), file.getOriginalFilename(), fileType, event);
            }
        }
    }

    private void saveEventFiles(String fileUrl, String originalFileName, FileType fileType, Event event) {
        eventFileRepository.save(EventFile.builder()
                .fileUrl(fileUrl)
                .fileName(originalFileName)
                .fileType(fileType)
                .event(event)
                .build());
    }

    private void validDateRange(LocalDate startDate, LocalDate endDate) {
        DefaultAssert.notNull(startDate, "시작 일자는 비어있으면 안 됩니다.");
        DefaultAssert.notNull(endDate, "종료 일자는 비어있으면 안 됩니다.");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료 일자는 시작 일자보다 빠를 수 없습니다.");
        }
    }

    @Transactional
    public void deleteEvent(UserPrincipal userPrincipal, Long eventId) {
        UserEntity user = validUserById(userPrincipal.getId());
        Event event = validEventById(eventId);
        event.updateIsDeleted(true);
        eventFileRepository.updateIsDeletedByEventId(eventId, true);
        List<EventGuide> eventGuides = eventGuideRepository.findByEventId(eventId);
        eventGuides.forEach(eventGuide -> {
            eventGuide.updateIsDeleted(true);
            eventGuideFileRepository.updateIsDeletedByEventGuideId(eventGuide.getId(), true);
        });
    }

    @Transactional
    public void deleteAllEvent(UserPrincipal userPrincipal) {
        eventRepository.updateIsDeletedForAll(true);
        eventFileRepository.updateIsDeletedForAll(true);
        eventGuideRepository.updateIsDeletedForAll(true);
        eventGuideFileRepository.updateIsDeletedForAll(true);
    }

    private UserEntity validUserById(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        DefaultAssert.isOptionalPresent(userOptional);
        return userOptional.get();
    }

    private Event validEventById(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        DefaultAssert.isOptionalPresent(eventOptional);
        return eventOptional.get();
    }
}