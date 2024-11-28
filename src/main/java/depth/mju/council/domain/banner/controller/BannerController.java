package depth.mju.council.domain.banner.controller;

import depth.mju.council.domain.banner.service.BannerService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;
    @Operation(summary = "배너 추가 API", description = "배너를 추가하는 API입니다.")
    @ApiResponses(value = {
    })
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResult> createBanner(
            @PathVariable Long userId,
            @RequestPart(value = "img", required = false) MultipartFile img) {
        bannerService.createBanner(userId, img);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("배너를 추가했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "배너 조회 API", description = "배너 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResult> getBanner(
            @PathVariable Long userId) {
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(bannerService.getBanner(userId))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "배너 수정 API", description = "배너 목록을 수정하는 API입니다.")
    @ApiResponses(value = {
    })
    @PatchMapping("/{bannerId}")
    public ResponseEntity<ApiResult> modifyBanner(
            @PathVariable Long bannerId,
            @RequestPart(value = "img", required = false) MultipartFile img) {
        bannerService.modifyBanner(bannerId, img);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("배너를 수정했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "배너 삭제 API", description = "배너를 삭제하는 API입니다.")
    @ApiResponses(value = {
    })
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<ApiResult> deleteBanner(
            @PathVariable Long bannerId) {
        bannerService.deleteBanner(bannerId);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("배너를 삭제했어요")
                .build();
        return ResponseEntity.ok(result);
    }

}