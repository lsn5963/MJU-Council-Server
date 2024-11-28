package depth.mju.council.domain.business.controller;

import depth.mju.council.domain.business.dto.req.CreateBusinessReq;
import depth.mju.council.domain.business.dto.req.ModifyBusinessReq;
import depth.mju.council.domain.business.service.BusinessService;
import depth.mju.council.domain.notice.dto.req.CreateNoticeReq;
import depth.mju.council.domain.notice.dto.req.ModifyNoticeReq;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @Operation(summary = "사업 등록")
    @PostMapping()
    public ResponseEntity<ApiResult> createBusiness(
            //@CurrentUser CustomUserDetails userDetails,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 이미지의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> images,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 파일의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> files,
            @Parameter(description = "Schemas의 CreateBusinessReq를 참고해주세요.", required = true) @Valid @RequestPart CreateBusinessReq createBusinessReq
            ) {
        businessService.createBusiness(images, files, createBusinessReq);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("사업이 등록되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "사업 전체 삭제")
    @DeleteMapping()
    public ResponseEntity<ApiResult> deleteAllBusiness(
            //@CurrentUser CustomUserDetails userDetails,
    ) {
        businessService.deleteAllBusiness();
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("사업이 전체 삭제되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "사업 삭제")
    @DeleteMapping("/{businessId}")
    public ResponseEntity<ApiResult> deleteAllBusiness(
            //@CurrentUser CustomUserDetails userDetails,
            @Parameter(description = "삭제하고자 하는 사업의 id를 입력해주세요.", required = true) @PathVariable Long businessId
    ) {
        businessService.deleteBusiness(businessId);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("사업이 삭제되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "사업 수정")
    @PutMapping("/{businessId}")
    public ResponseEntity<ApiResult> modifyBusiness(
            //@CurrentUser CustomUserDetails userDetails,
            @Parameter(description = "수정하고자 하는 사업의 id를 입력해주세요.", required = true) @PathVariable Long businessId,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 이미지의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> images,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 파일의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> files,
            @Parameter(description = "Schemas의 ModifyBusinessReq를 참고해주세요.", required = true) @Valid @RequestPart ModifyBusinessReq modifyBusinessReq
    ) {
        businessService.modifyBusiness(businessId, images, files, modifyBusinessReq);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("사업이 수정되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }
}
