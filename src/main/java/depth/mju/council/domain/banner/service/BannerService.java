package depth.mju.council.domain.banner.service;

import depth.mju.council.domain.banner.dto.res.GetBannerRes;
import depth.mju.council.domain.banner.entity.Banner;
import depth.mju.council.domain.banner.repository.BannerRepository;
import depth.mju.council.domain.user.entity.UserEntity;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.DefaultAssert;
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
public class BannerService {
    private final UserRepository userRepository;
    private final BannerRepository bannerRepository;
    @Transactional
    public void createBanner(Long userId, MultipartFile img) {
        UserEntity user = validUserById(userId);;
        Banner banner = Banner.builder()
                .imgUrl("이미지 URL 저장 로직 필요")
                .userEntity(user)
                .build();
        bannerRepository.save(banner);
    }
    public List<GetBannerRes> getBanner() {
        List<Banner> banners = bannerRepository.findAll();
        List<GetBannerRes> bannersRes = banners.stream()
                .map(banner -> GetBannerRes.builder()
                        .bannerId(banner.getId())
                        .imgUrl(banner.getImgUrl())
                        .date(banner.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return bannersRes;
    }
    @Transactional
    public void modifyBanner(Long bannerId, MultipartFile img) {
        Banner banner = validaBannerById(bannerId);
        // 이미지 URL 업데이트 로직 필요
        banner.updateImgUrl("새로운 이미지 URL");
    }
    @Transactional
    public void deleteBanner(Long bannerId) {
        Banner banner = validaBannerById(bannerId);
        bannerRepository.delete(banner);
    }
    private UserEntity validUserById(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        DefaultAssert.isOptionalPresent(userOptional);
        return userOptional.get();
    }
    private Banner validaBannerById(Long bannerId) {
        Optional<Banner> bannerOptional = bannerRepository.findById(bannerId);
        DefaultAssert.isOptionalPresent(bannerOptional);
        return bannerOptional.get();
    }
}