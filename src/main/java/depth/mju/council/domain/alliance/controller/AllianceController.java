package depth.mju.council.domain.alliance.controller;

import depth.mju.council.domain.alliance.dto.req.CreateAllianceReq;
import depth.mju.council.domain.alliance.dto.req.ModifyAllianceReq;
import depth.mju.council.domain.alliance.service.AllianceService;
import depth.mju.council.global.config.UserPrincipal;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alliances")
@RequiredArgsConstructor
public class AllianceController {
    private final AllianceService allianceService;

    @Operation(summary = "제휴 등록")
    @PostMapping()
    public ResponseEntity<ApiResult> createAlliance(
            @Parameter(description = "User의 토큰을 입력해주세요.", required = true) @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 이미지의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> images,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 파일의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> files,
            @Parameter(description = "Schemas의 CreateAllianceReq를 참고해주세요.", required = true) @Valid @RequestPart CreateAllianceReq createAllianceReq
            ) {
        allianceService.createAlliance(userPrincipal, images, files, createAllianceReq);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("제휴가 등록되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "제휴 수정")
    @PutMapping("/{allianceId}")
    public ResponseEntity<ApiResult> modifyAlliance(
            @Parameter(description = "User의 토큰을 입력해주세요.", required = true) @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(description = "수정하고자 하는 제휴의 id를 입력해주세요.", required = true) @PathVariable Long allianceId,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 이미지의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> images,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 파일의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> files,
            @Parameter(description = "Schemas의 ModifyAllianceReq를 참고해주세요.", required = true) @Valid @RequestPart ModifyAllianceReq modifyAllianceReq
            ) {
        allianceService.modifyAlliance(allianceId, images, files, modifyAllianceReq);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("제휴가 수정되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }
}
