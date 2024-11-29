package depth.mju.council.domain.orgarnization.service;

import depth.mju.council.domain.orgarnization.dto.res.OrganizationRes;
import depth.mju.council.domain.orgarnization.entity.Organization;
import depth.mju.council.domain.orgarnization.repository.OrganizationRepository;
import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.config.UserPrincipal;
import depth.mju.council.global.error.DefaultException;
import depth.mju.council.global.payload.ErrorCode;
import depth.mju.council.infrastructure.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    public List<OrganizationRes> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();

        return organizations.stream()
                .map(organization -> OrganizationRes.builder()
                        .organizationId(organization.getId())
                        .title(organization.getTitle())
                        .imgUrl(organization.getImgUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public void createOrganizations(List<String> titles, List<MultipartFile> images, UserPrincipal userPrincipal) {
        if (titles.size() != images.size()) {
            throw new DefaultException(ErrorCode.INVALID_PARAMETER, "제목의 개수와 이미지의 개수가 일치하지 않습니다.");
        }

        UserEntity user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND));

        for (int i = 0; i < titles.size(); i++) {
            String imgUrl = s3Service.uploadImage(images.get(i));
            Organization organization = Organization.builder()
                    .title(titles.get(i))
                    .imgUrl(imgUrl)
                    .userEntity(user)
                    .build();
            organizationRepository.save(organization);
        }
    }

}