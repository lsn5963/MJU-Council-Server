package depth.mju.council.domain.orgarnization.controller;

import depth.mju.council.domain.orgarnization.service.OrganizationService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @Operation(summary = "조직도 조회")
    @GetMapping("")
    public ResponseEntity<ApiResult> getAllOrgarnizations() {
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .information(organizationService.getAllOrganizations())
                .message("조직도를 성공적으로 조회했습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }
}
