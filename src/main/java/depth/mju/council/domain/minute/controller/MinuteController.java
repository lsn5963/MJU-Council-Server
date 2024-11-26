package depth.mju.council.domain.minute.controller;

import depth.mju.council.domain.minute.dto.req.CreateMinuteReq;
import depth.mju.council.domain.minute.dto.req.ModifyMinuteReq;
import depth.mju.council.domain.minute.service.MinuteService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/minutes")
@RequiredArgsConstructor
public class MinuteController {
    private final MinuteService minuteService;
    @Operation(summary = "공약 추가 API", description = "공약 목록을 추가하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/{userId}")
    public ResponseEntity<?> createMinute(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long userId,
            @RequestPart(value = "imgs", required = false) List<MultipartFile> imgs
    , @RequestBody CreateMinuteReq createMinuteReq){
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(minuteService.createMinute(userId,imgs,createMinuteReq))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "회의록 전체 조회 API", description = "회의록 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
    })
    @GetMapping("/all/{userId}")
    public ResponseEntity<?> retrieveAllMinute(
            @PathVariable Long userId) {
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(minuteService.retrieveAllMinute(userId))
                .build();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "회의록 상세 조회 API", description = "회의록 상세 내용을 조회하는 API입니다.")
    @ApiResponses(value = {
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> retrieveMinute(
            @PathVariable Long userId) {
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(minuteService.retrieveMinute(userId))
                .build();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "회의록 수정 API", description = "회의록을 수정하는 API입니다.")
    @ApiResponses(value = {
    })
    @PatchMapping("/{minuteId}")
    public ResponseEntity<?> modifyMinute(
            @PathVariable Long minuteId,
            @RequestBody ModifyMinuteReq modifyMinuteReq,
            @RequestPart(value = "imgs", required = false) List<MultipartFile> imgs) {
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(minuteService.modifyMinute(minuteId, modifyMinuteReq, imgs))
                .build();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "회의록 삭제 API", description = "회의록을 삭제하는 API입니다.")
    @ApiResponses(value = {
    })
    @DeleteMapping("/{minuteId}")
    public ResponseEntity<?> deleteMinute(
            @PathVariable Long minuteId) {
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(minuteService.deleteMinute(minuteId))
                .build();
        return ResponseEntity.ok(result);
    }
}