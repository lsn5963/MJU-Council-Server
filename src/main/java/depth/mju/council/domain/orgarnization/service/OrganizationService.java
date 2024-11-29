package depth.mju.council.domain.orgarnization.service;

import depth.mju.council.domain.orgarnization.dto.res.OrganizationRes;
import depth.mju.council.domain.orgarnization.entity.Organization;
import depth.mju.council.domain.orgarnization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}