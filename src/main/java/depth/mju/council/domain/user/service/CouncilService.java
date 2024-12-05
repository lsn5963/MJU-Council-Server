package depth.mju.council.domain.user.service;

import depth.mju.council.domain.committe.dto.req.CreateCommitteeReq;
import depth.mju.council.domain.committe.entity.Committee;
import depth.mju.council.domain.user.dto.req.UpdateCouncilReq;
import depth.mju.council.domain.user.dto.res.CouncilRes;
import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.config.UserPrincipal;
import depth.mju.council.global.error.DefaultException;
import depth.mju.council.global.payload.ErrorCode;
import depth.mju.council.infrastructure.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class CouncilService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public CouncilRes getCouncil() {
        UserEntity user = userRepository.findByIsDeleted(false);
        return CouncilRes.builder()
                .generation(user.getGeneration())
                .name(user.getName())
                .email(user.getEmail())
                .snsUrl(user.getSnsUrl())
                .logoUrl(user.getLogoUrl())
                .build();
    }

    @Transactional
    public void updateCouncil(UpdateCouncilReq request, MultipartFile image,
                                UserPrincipal userPrincipal) {
        UserEntity user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND));

        // 기존 이미지 삭제
        String oldImageUrl = user.getLogoUrl();
        if (oldImageUrl != null) {
            String oldImageName = s3Service.extractImageNameFromUrl(oldImageUrl);
            s3Service.deleteImage(oldImageName);
        }

        // 새 이미지 업로드
        String newImageUrl = s3Service.uploadImage(image);
        user.updateCouncil(request.getGeneration(), request.getName(), request.getEmail(), request.getSnsUrl(),
                newImageUrl);

        userRepository.save(user);
    }
}