package depth.mju.council.domain.business.service;

import depth.mju.council.domain.business.dto.req.CreateBusinessReq;
import depth.mju.council.domain.business.entity.Business;
import depth.mju.council.domain.business.entity.BusinessFile;
import depth.mju.council.domain.business.repository.BusinessFileRepository;
import depth.mju.council.domain.business.repository.BusinessRepository;
import depth.mju.council.domain.common.FileType;
import depth.mju.council.domain.notice.dto.req.CreateNoticeReq;
import depth.mju.council.domain.notice.entity.Notice;
import depth.mju.council.domain.notice.entity.NoticeFile;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.infrastructure.s3.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
}