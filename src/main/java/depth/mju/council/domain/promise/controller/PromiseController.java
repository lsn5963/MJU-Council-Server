package depth.mju.council.domain.promise.controller;

import depth.mju.council.domain.promise.service.PromiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policy")
@RequiredArgsConstructor
public class PromiseController {
    private final PromiseService promiseService;
    @Operation(summary = "장책 추가 API", description = "정책 목록을 추가하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/{id}/{policyTitle}")
    public ResponseEntity<?> createPromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id, @PathVariable String policyTitle){
        return promiseService.createPromise(id, policyTitle);
    }
    @Operation(summary = "장책 조회 API", description = "정책 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> retrievePromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id){
        return promiseService.retrievePromise(id);
    }
    @Operation(summary = "장책 수정 API", description = "정책 목록을 수정하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PatchMapping("/{id}/{policyId}/{policyTitle}")
    public ResponseEntity<?> modifyPromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id,
            @PathVariable Long policyId,
            @PathVariable String policyTitle){
        return promiseService.modifyPromise(id,policyId,policyTitle);
    }
    @Operation(summary = "장책 삭제 API", description = "정책 목록을 삭제하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @DeleteMapping("/{id}/{policyId}")
    public ResponseEntity<?> deletePromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id,
            @PathVariable Long policyId){
        return promiseService.deletePromise(id,policyId);
    }
}
