package depth.mju.council.domain.committe.controller;

import depth.mju.council.domain.committe.service.CommitteeService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/committees")
@RequiredArgsConstructor
public class CommitteeController {
    private final CommitteeService committeeService;

    @Operation(summary = "중운위(단과대) 조회")
    @GetMapping("")
    public ResponseEntity<ApiResult> getAllCommittees() {
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .information(committeeService.getAllCommittees())
                .message("중운위(단과대)를 성공적으로 조회했습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }
}
