package depth.mju.council.domain.user.controller;

import depth.mju.council.domain.committe.dto.req.CreateCommitteeReq;
import depth.mju.council.domain.user.dto.req.UpdateCouncilReq;
import depth.mju.council.domain.user.service.CouncilService;
import depth.mju.council.domain.user.service.UserService;
import depth.mju.council.global.config.UserPrincipal;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/council")
@RequiredArgsConstructor
public class CouncilController {

    private final CouncilService councilService;

    @Operation(summary = "총학정보 조회")
    @GetMapping("")
    public ResponseEntity<ApiResult> getCouncil() {
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .information(councilService.getCouncil())
                .message("총학정보를 성공적으로 조회했습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }
}
