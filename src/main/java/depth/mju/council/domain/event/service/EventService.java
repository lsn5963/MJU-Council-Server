package depth.mju.council.domain.event.service;

import depth.mju.council.domain.common.FileType;
import depth.mju.council.domain.event.dto.req.CreateEventReq;
import depth.mju.council.domain.event.dto.res.EventDetailListRes;
import depth.mju.council.domain.event.dto.res.EventListRes;
import depth.mju.council.domain.event.dto.res.EventRes;
import depth.mju.council.domain.event.entity.Event;
import depth.mju.council.domain.event.entity.EventFile;
import depth.mju.council.domain.event.entity.EventDetail;
import depth.mju.council.domain.event.repository.EventFileRepository;
import depth.mju.council.domain.event.repository.EventDetailFileRepository;
import depth.mju.council.domain.event.repository.EventDetailRepository;
import depth.mju.council.domain.event.repository.EventRepository;
import depth.mju.council.domain.event.dto.req.ModifyEventReq;
import depth.mju.council.domain.notice.dto.res.FileRes;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;
    private final EventFileRepository eventFileRepository;
    private final EventDetailRepository eventDetailRepository;
    private final EventDetailFileRepository eventDetailFileRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;

    public EventRes getEvent(Long eventId) {
        Event event = validEventById(eventId);
        List<FileRes> images = eventFileRepository.findEventFilesByEventIdAndFileType(eventId, FileType.IMAGE);
        return EventRes.builder()
                .title(event.getTitle())
                .content(event.getContent())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .images(images)
                .eventDetails(findEventDetail(eventId))
                .build();
    }

    private List<EventDetailListRes> findEventDetail(Long eventId) {
        return eventDetailRepository.findEventDetailsByEventId(eventId, false);
    }

    public List<EventListRes> getAllEvent() {
        return eventRepository.findEventsByIsDeleted(false);
    }

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
    public void deleteEvent(Long eventId) {
        Event event = validEventById(eventId);
        event.updateIsDeleted(true);
        eventFileRepository.updateIsDeletedByEventId(eventId, true);
        List<EventDetail> eventDetails = eventDetailRepository.findByEventId(eventId);
        eventDetails.forEach(eventDetail -> {
            eventDetail.updateIsDeleted(true);
            eventDetailFileRepository.updateIsDeletedByEventDetailId(eventDetail.getId(), true);
        });
    }

    @Transactional
    public void deleteAllEvent() {
        eventRepository.updateIsDeletedForAll(true);
        eventFileRepository.updateIsDeletedForAll(true);
        eventDetailRepository.updateIsDeletedForAll(true);
        eventDetailFileRepository.updateIsDeletedForAll(true);
    }

    @Transactional
    public void modifyEvent(Long eventId, List<MultipartFile> images, ModifyEventReq modifyEventReq) {
        Event event = validEventById(eventId);
        validDateRange(modifyEventReq.getStartDate(), modifyEventReq.getEndDate());
        event.updateTitleAndContentAndStartAndEndDate(modifyEventReq.getTitle(), modifyEventReq.getContent(), modifyEventReq.getStartDate(), modifyEventReq.getEndDate());

        deleteEventFiles(modifyEventReq.getDeleteImages(), FileType.IMAGE);
        uploadEventFiles(images, event, FileType.IMAGE);
    }

    private void deleteEventFiles(List<Integer> files, FileType fileType) {
        if (files == null || files.isEmpty()) {
            return;
        }
        List<Long> fileIds = files.stream().map(Long::valueOf).collect(Collectors.toList());
        List<EventFile> filesToDelete = eventFileRepository.findAllById(fileIds);
        filesToDelete.forEach(file -> {
            String saveFileName = extractSaveFileName(file.getFileUrl());
            // if (fileType == FileType.FILE) {
            //     s3Service.deleteFile(saveFileName);
            // } else {
                s3Service.deleteImage(saveFileName);
            // }
            // DB에서 삭제
            eventFileRepository.delete(file);
        });
    }

    public String extractSaveFileName(String fileUrl) {
        String[] parts = fileUrl.split("/");
        return parts[parts.length - 1];
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