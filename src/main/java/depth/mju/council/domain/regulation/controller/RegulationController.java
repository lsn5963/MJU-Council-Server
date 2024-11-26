package depth.mju.council.domain.regulation.controller;

import depth.mju.council.domain.regulation.service.RegulationService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/regulations")
@RequiredArgsConstructor
public class RegulationController {
    private final RegulationService regulationService;
    @Operation(summary = "학생회칙 추가 API", description = "학생회칙을 추가하는 API입니다.")
    @ApiResponses(value = {
    })
    @PostMapping("/{userId}")
    public ResponseEntity<?> createRegulation(
            @PathVariable Long userId,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        regulationService.createRegulation(userId, file);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("학생회칙을 추가했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "학생회칙 조회 API", description = "학생회칙 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> retrieveAllRegulation(
            @PathVariable Long userId) {
        //페에징 필요
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(regulationService.retrieveAllRegulation(userId))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "학생회칙 수정 API", description = "학생회칙을 수정하는 API입니다.")
    @ApiResponses(value = {
    })
    @PatchMapping("/{regulationId}")
    public ResponseEntity<?> modifyRegulation(
            @PathVariable Long regulationId,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        regulationService.modifyRegulation(regulationId, file);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("학생회칙을 수정했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "학생회칙 삭제 API", description = "학생회칙을 삭제하는 API입니다.")
    @ApiResponses(value = {
    })
    @DeleteMapping("/{regulationId}")
    public ResponseEntity<?> deleteRegulation(
            @PathVariable Long regulationId) {
        regulationService.deleteRegulation(regulationId);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("학생회칙을 삭제했어요")
                .build();
        return ResponseEntity.ok(result);
    }
}
