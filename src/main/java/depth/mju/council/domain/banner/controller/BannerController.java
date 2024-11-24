package depth.mju.council.domain.banner.controller;

import depth.mju.council.domain.banner.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;
    @Operation(summary = "배너 추가 API", description = "배너를 추가하는 API입니다.")
    @ApiResponses(value = {
    })
    @PostMapping("/{id}")
    public ResponseEntity<?> createBanner(
            @PathVariable Long id,
            @RequestPart(value = "img", required = false) MultipartFile img){
        return bannerService.createBanner(id, img);
    }
    @Operation(summary = "배너 조회 API", description = "배너 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveBanner(
            @PathVariable Long id) {
        return bannerService.retrieveBanner(id);
    }
    @PatchMapping("/{bannerId}")
    public ResponseEntity<?> modifyBanner(
            @PathVariable Long bannerId,
            @RequestPart(value = "img", required = false) MultipartFile img) {
        return bannerService.modifyBanner(bannerId, img);
    }
    @Operation(summary = "배너 삭제 API", description = "배너를 삭제하는 API입니다.")
    @ApiResponses(value = {
    })
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<?> deleteBanner(
            @PathVariable Long bannerId) {
        return bannerService.deleteBanner(bannerId);
    }

}