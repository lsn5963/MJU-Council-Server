package depth.mju.council.domain.banner.service;

import depth.mju.council.domain.banner.dto.res.RetrieveBannerRes;
import depth.mju.council.domain.banner.entity.Banner;
import depth.mju.council.domain.banner.repository.BannerRepository;
import depth.mju.council.domain.user.entity.User;
import depth.mju.council.domain.user.repository.UserRepository;
import depth.mju.council.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final UserRepository userRepository;
    private final BannerRepository bannerRepository;
    public ResponseEntity<?> createBanner(Long id, MultipartFile img) {
        User user = userRepository.findById(id).get();
        Banner banner = Banner.builder()
                .imgUrl("이미지 URL 저장 로직 필요")
                .user(user)
                .build();
        bannerRepository.save(banner);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("배너를 추가했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    public ResponseEntity<?> retrieveBanner(Long id) {
        User user = userRepository.findById(id).get();
        List<Banner> banners = bannerRepository.findByUser(user);
        List<RetrieveBannerRes> bannersRes = banners.stream()
                .map(banner -> RetrieveBannerRes.builder()
                        .id(banner.getId())
                        .imgUrl(banner.getImgUrl())
                        .date(banner.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(bannersRes)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @Transactional
    public ResponseEntity<?> modifyBanner(Long bannerId, MultipartFile img) {
        Banner banner = bannerRepository.findById(bannerId).get();
        // 이미지 URL 업데이트 로직 필요
        banner.updateImgUrl("새로운 이미지 URL");

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("배너를 수정했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    public ResponseEntity<?> deleteBanner(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).get();
        bannerRepository.delete(banner);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information("배너를 삭제했어요")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}