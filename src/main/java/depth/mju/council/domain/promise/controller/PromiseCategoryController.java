package depth.mju.council.domain.promise.controller;

import depth.mju.council.domain.promise.service.PromiseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policy")
@RequiredArgsConstructor
public class PromiseCategoryController {
    private final PromiseCategoryService promiseService;
    @Operation(summary = "정책 추가 API", description = "정책 목록을 추가하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/{id}/{policyTitle}")
    public ResponseEntity<?> createPromiseCategory(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id, @PathVariable String policyTitle){
        return promiseService.createPromiseCategory(id, policyTitle);
    }
    @Operation(summary = "정책 조회 API", description = "정책 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> retrievePromiseCategory(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id){
        return promiseService.retrievePromiseCategory(id);
    }
    @Operation(summary = "정책 수정 API", description = "정책 목록을 수정하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PatchMapping("/{id}/{policyId}/{policyTitle}")
    public ResponseEntity<?> modifyPromiseCategory(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id,
            @PathVariable Long policyId,
            @PathVariable String policyTitle){
        return promiseService.modifyPromiseCategory(id,policyId,policyTitle);
    }
    @Operation(summary = "정책 삭제 API", description = "정책 목록을 삭제하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @DeleteMapping("/{id}/{policyId}")
    public ResponseEntity<?> deletePromiseCategory(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id,
            @PathVariable Long policyId){
        return promiseService.deletePromiseCategory(id,policyId);
    }

}