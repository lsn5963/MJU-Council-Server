package depth.mju.council.domain.alliance.service;

import depth.mju.council.domain.alliance.dto.req.CreateAllianceReq;
import depth.mju.council.domain.alliance.entity.Alliance;
import depth.mju.council.domain.alliance.entity.AllianceFile;
import depth.mju.council.domain.alliance.repository.AllianceFileRepository;
import depth.mju.council.domain.alliance.repository.AllianceRepository;
import depth.mju.council.domain.common.FileType;
import depth.mju.council.domain.event.dto.req.CreateEventReq;
import depth.mju.council.domain.event.entity.Event;
import depth.mju.council.domain.event.entity.EventFile;
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
public class AllianceService {

    private final AllianceRepository allianceRepository;
    private final AllianceFileRepository allianceFileRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;

    @Transactional
    public void createAlliance(
            UserPrincipal userPrincipal,
            List<MultipartFile> images, List<MultipartFile> files, CreateAllianceReq createAllianceReq)
    {
        UserEntity user = validUserById(userPrincipal.getId());
        validDateRange(createAllianceReq.getStartDate(), createAllianceReq.getEndDate());
        Alliance alliance = Alliance.builder()
                .title(createAllianceReq.getTitle())
                .content(createAllianceReq.getContent())
                .startDate(createAllianceReq.getStartDate())
                .endDate(createAllianceReq.getEndDate())
                .userEntity(user)
                .build();
        allianceRepository.save(alliance);
        uploadAllianceFiles(images, alliance, FileType.IMAGE);
        uploadAllianceFiles(files, alliance, FileType.FILE);
    }

    private void uploadAllianceFiles(List<MultipartFile> files, Alliance alliance, FileType fileType) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                saveAllianceFiles(s3Service.uploadFile(file), file.getOriginalFilename(), fileType, alliance);
            }
        }
    }

    private void saveAllianceFiles(String fileUrl, String originalFileName, FileType fileType, Alliance alliance) {
        allianceFileRepository.save(AllianceFile.builder()
                .fileUrl(fileUrl)
                .fileName(originalFileName)
                .fileType(fileType)
                .alliance(alliance)
                .build());
    }

    private void validDateRange(LocalDate startDate, LocalDate endDate) {
        DefaultAssert.notNull(startDate, "시작 일자는 비어있으면 안 됩니다.");
        DefaultAssert.notNull(endDate, "종료 일자는 비어있으면 안 됩니다.");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료 일자는 시작 일자보다 빠를 수 없습니다.");
        }
    }

    private UserEntity validUserById(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        DefaultAssert.isOptionalPresent(userOptional);
        return userOptional.get();
    }
}