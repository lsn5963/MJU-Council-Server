package depth.mju.council.domain.notice.service;

import depth.mju.council.domain.common.FileType;
import depth.mju.council.domain.notice.dto.req.NoticeRequest;
import depth.mju.council.domain.notice.entity.Notice;
import depth.mju.council.domain.notice.entity.NoticeFile;
import depth.mju.council.domain.notice.repository.NoticeFileRepository;
import depth.mju.council.domain.notice.repository.NoticeRepository;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.DefaultAssert;
import depth.mju.council.infrastructure.s3.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final S3Uploader s3Uploader;

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

    @Transactional
    public void createNotice(
            List<MultipartFile> images, List<MultipartFile> files, NoticeRequest noticeRequest)
    {
        User user = userRepository.findById(1L).get(); // 임시

        Notice notice = Notice.builder()
                .title(noticeRequest.getTitle())
                .content(noticeRequest.getContent())
                .user(user)
                .build();
        noticeRepository.save(notice);

        uploadNoticeImages(images, notice);
        uploadNoticeFiles(files, notice);

    }

    private void uploadNoticeImages(List<MultipartFile> images, Notice notice) {
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileUrl = s3Uploader.uploadImage(image);
                saveNoticeFiles(fileUrl, image.getOriginalFilename(), FileType.IMAGE, notice);
            }
        }
    }

    private void uploadNoticeFiles(List<MultipartFile> files, Notice notice) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileUrl = s3Uploader.uploadFile(file);
                saveNoticeFiles(fileUrl, file.getOriginalFilename(), FileType.FILE, notice);
            }
        }
    }

    private void saveNoticeFiles(String fileUrl, String originalFileName, FileType fileType, Notice notice) {
        noticeFileRepository.save(NoticeFile.builder()
                .fileUrl(fileUrl)
                .fileName(originalFileName)
                .fileType(fileType)
                .notice(notice)
                .build());
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        Notice notice = validNoticeById(noticeId);
        // SOFT DELETE로 구현
        notice.updateIsDeleted(true);
        noticeFileRepository.updateIsDeletedByNoticeId(noticeId, true);
    }

    @Transactional
    public void deleteAllNotice() {
        noticeRepository.updateIsDeletedForAll(true);
        noticeFileRepository.updateIsDeletedForAll(true);
    }

    private Notice validNoticeById(Long noticeId) {
        Optional<Notice> noticeOptional = noticeRepository.findById(noticeId);
        DefaultAssert.isOptionalPresent(noticeOptional);
        return noticeOptional.get();
    }

}