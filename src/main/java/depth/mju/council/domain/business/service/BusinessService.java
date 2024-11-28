package depth.mju.council.domain.business.service;

import depth.mju.council.domain.business.dto.req.CreateBusinessReq;
import depth.mju.council.domain.business.dto.req.ModifyBusinessReq;
import depth.mju.council.domain.business.entity.Business;
import depth.mju.council.domain.business.entity.BusinessFile;
import depth.mju.council.domain.business.repository.BusinessFileRepository;
import depth.mju.council.domain.business.repository.BusinessRepository;
import depth.mju.council.domain.common.FileType;
import depth.mju.council.domain.notice.dto.req.CreateNoticeReq;
import depth.mju.council.domain.notice.dto.req.ModifyNoticeReq;
import depth.mju.council.domain.notice.entity.Notice;
import depth.mju.council.domain.notice.entity.NoticeFile;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessService {

    private final S3Uploader s3Uploader;

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final BusinessFileRepository businessFileRepository;

    @Transactional
    public void createBusiness (
            List<MultipartFile> images, List<MultipartFile> files, CreateBusinessReq createBusinessReq)
    {
        User user = userRepository.findById(1L).get(); // 임시

        Business business = Business.builder()
                .title(createBusinessReq.getTitle())
                .content(createBusinessReq.getContent())
                .user(user)
                .build();
        businessRepository.save(business);

        uploadBusinessFiles(images, business, FileType.IMAGE);
        uploadBusinessFiles(files, business, FileType.FILE);

    }

    private void uploadBusinessFiles(List<MultipartFile> files, Business business, FileType fileType) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                saveBusinessFiles(s3Uploader.uploadFile(file), file.getOriginalFilename(), fileType, business);
            }
        }
    }

    private void saveBusinessFiles(String fileUrl, String originalFileName, FileType fileType, Business business) {
        businessFileRepository.save(BusinessFile.builder()
                .fileUrl(fileUrl)
                .fileName(originalFileName)
                .fileType(fileType)
                .business(business)
                .build());
    }

    @Transactional
    public void deleteBusiness(Long noticeId) {
        Business business = validBusinessById(noticeId);
        // SOFT DELETE로 구현
        business.updateIsDeleted(true);
        businessFileRepository.updateIsDeletedByBusinessId(noticeId, true);
    }

    @Transactional
    public void deleteAllBusiness() {
        businessRepository.updateIsDeletedForAll(true);
        businessFileRepository.updateIsDeletedForAll(true);
    }

    @Transactional
    public void modifyBusiness(Long noticeId, List<MultipartFile> images, List<MultipartFile> files, ModifyBusinessReq modifyBusinessReq) {
        Business business = validBusinessById(noticeId);
        // Notice 정보 변경
        business.updateTitleAndContent(modifyBusinessReq.getTitle(), modifyBusinessReq.getContent());
        // 지우고자 하는 이미지/파일 삭제
        deleteBusinessFiles(modifyBusinessReq.getDeleteFiles(), FileType.FILE);
        deleteBusinessFiles(modifyBusinessReq.getDeleteImages(), FileType.IMAGE);
        // 파일/이미지 업로드
        uploadBusinessFiles(images, business, FileType.IMAGE);
        uploadBusinessFiles(files, business, FileType.FILE);
    }

    private void deleteBusinessFiles(List<Integer> files, FileType fileType) {
        if (files == null || files.isEmpty()) {
            return;
        }
        List<Long> fileIds = files.stream().map(Long::valueOf).collect(Collectors.toList());
        List<BusinessFile> filesToDelete = businessFileRepository.findAllById(fileIds);
        filesToDelete.forEach(file -> {
            // 저장 파일명 구하기
            String saveFileName = extractSaveFileName(file.getFileUrl());
            // S3에서 삭제
            if (fileType == FileType.FILE) {
                s3Uploader.deleteFile(saveFileName);
            } else {
                s3Uploader.deleteImage(saveFileName);
            }
            // DB에서 삭제
            businessFileRepository.delete(file);
        });
    }

    public String extractSaveFileName(String fileUrl) {
        String[] parts = fileUrl.split("/");
        return parts[parts.length - 1];
    }

    private Business validBusinessById(Long businessId) {
        Optional<Business> businessOptional = businessRepository.findByIdAndIsDeleted(businessId, false);
        DefaultAssert.isOptionalPresent(businessOptional);
        return businessOptional.get();
    }
}