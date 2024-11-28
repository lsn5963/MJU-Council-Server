package depth.mju.council.domain.promise.controller;

import depth.mju.council.domain.promise.service.PromiseCategoryService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/promise-category")
@RequiredArgsConstructor
public class PromiseCategoryController {
    private final PromiseCategoryService promiseCategoryService;
    @Operation(summary = "정책 추가 API", description = "정책 목록을 추가하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/{userId}/{promiseTitle}")
    public ResponseEntity<ApiResult> createPromiseCategory(
            @PathVariable Long userId,
            @PathVariable String promiseTitle) {
        promiseCategoryService.createPromiseCategory(userId, promiseTitle);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("정책 목록을 추가했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "정책 조회 API", description = "정책 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResult> getPromiseCategory(@PathVariable Long userId) {
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(promiseCategoryService.getPromiseCategory(userId))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "정책 수정 API", description = "정책 목록을 수정하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PatchMapping("/{promiseId}/{promiseTitle}")
    public ResponseEntity<ApiResult> modifyPromiseCategory(
            @PathVariable Long promiseId,
            @PathVariable String promiseTitle) {
        promiseCategoryService.modifyPromiseCategory(promiseId, promiseTitle);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("정책 목록을 수정했어요")
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "정책 삭제 API", description = "정책 목록을 삭제하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @DeleteMapping("/{promiseId}")
    public ResponseEntity<ApiResult> deletePromiseCategory(
            @PathVariable Long promiseId) {
        promiseCategoryService.deletePromiseCategory(promiseId);
        ApiResult result = ApiResult.builder()
                .check(true)
                .information("정책 목록을 삭제했어요")
                .build();
        return ResponseEntity.ok(result);
    }

}
