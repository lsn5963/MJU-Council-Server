package depth.mju.council.domain.promise.controller;

import depth.mju.council.domain.promise.service.PromiseCategoryService;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promise-category")
@RequiredArgsConstructor
public class PromiseCategoryController {
    private final PromiseCategoryService promiseService;
    @Operation(summary = "정책 추가 API", description = "정책 목록을 추가하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/{userId}/{promiseTitle}")
    public ResponseEntity<?> createPromiseCategory(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long userId, @PathVariable String promiseTitle){
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(promiseService.createPromiseCategory(userId, promiseTitle))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "정책 조회 API", description = "정책 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> retrievePromiseCategory(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long userId){
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(promiseService.retrievePromiseCategory(userId))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "정책 수정 API", description = "정책 목록을 수정하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PatchMapping("/{promiseId}/{promiseTitle}")
    public ResponseEntity<?> modifyPromiseCategory(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long promiseId,
            @PathVariable String promiseTitle){
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(promiseService.modifyPromiseCategory(promiseId,promiseTitle))
                .build();
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "정책 삭제 API", description = "정책 목록을 삭제하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResult(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResult(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @DeleteMapping("/{promiseId}")
    public ResponseEntity<?> deletePromiseCategory(
            @PathVariable Long promiseId){
        ApiResult result = ApiResult.builder()
                .check(true)
                .information(promiseService.deletePromiseCategory(promiseId))
                .build();
        return ResponseEntity.ok(result);
    }

}
