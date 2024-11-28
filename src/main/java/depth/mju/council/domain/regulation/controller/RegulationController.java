package depth.mju.council.domain.regulation.controller;

import depth.mju.council.domain.regulation.service.RegulationService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/regulations")
@RequiredArgsConstructor
public class RegulationController {
    private final RegulationService regulationService;
    @Operation(summary = "학생회칙 추가 API", description = "학생회칙을 추가하는 API입니다.")
    @ApiResponses(value = {
    })
    @PostMapping("/{userId}/{revisionDate}")
    public ResponseEntity<?> createRegulation(
            @PathVariable Long userId,
            @PathVariable LocalDate revisionDate,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        regulationService.createRegulation(userId, file,revisionDate);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("학생회칙을 추가했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "학생회칙 전체 조회 API", description = "학생회칙 목록을 전체 조회하는 API입니다.")
    @ApiResponses(value = {
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> retrieveAllRegulation(
            @PathVariable Long userId,
            @Parameter(description = "현재 페이지의 번호입니다. 0부터 시작합니다.", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "한 페이지의 개수입니다.", required = true) @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "검색어입니다. 검색하지 않을 경우, 값을 보내지 않습니다.", required = false) @RequestParam Optional<String> keyword) {
        //페이징 필요
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(regulationService.retrieveAllRegulation(keyword, page, size))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "학생회칙 상세 조회 API", description = "학생회칙 상세 내용을 조회하는 API입니다.")
    @ApiResponses(value = {
    })
    @GetMapping("/{regulationId}")
    public ResponseEntity<?> retrieveRegulation(
            @PathVariable Long regulationId) {
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(regulationService.retrieveRegulation(regulationId))
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
