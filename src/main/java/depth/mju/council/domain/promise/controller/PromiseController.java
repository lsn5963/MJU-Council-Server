package depth.mju.council.domain.promise.controller;

import depth.mju.council.domain.promise.dto.req.CreatePromiseReq;
import depth.mju.council.domain.promise.dto.req.ModifyPromiseReq;
import depth.mju.council.domain.promise.service.PromiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/policy/promise")
public class PromiseController {
    private final PromiseService promiseService;
    @Operation(summary = "공약 추가 API", description = "공약 목록을 추가하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/{id}/{policyTitle}")
    public ResponseEntity<?> createPromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id, @PathVariable String policyTitle, @RequestBody CreatePromiseReq createPromiseCategoryReq){
        return promiseService.createPromise(id, policyTitle,createPromiseCategoryReq);
    }
    @Operation(summary = "공약 조회 API", description = "공약 목록을 조회하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/{id}/{policyTitle}")
    public ResponseEntity<?> retrievePromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long id, @PathVariable String policyTitle){
        return promiseService.retrievePromise(id, policyTitle);
    }
    @Operation(summary = "공약 수정 API", description = "공약 목록을 수정하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PatchMapping("/{promiseId}")
    public ResponseEntity<?> modifyPromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long promiseId,@RequestBody ModifyPromiseReq modifyPromiseReq){
        return promiseService.modifyPromise(promiseId,modifyPromiseReq);
    }
    @Operation(summary = "공약 삭제 API", description = "공약 목록을 삭제하는 API입니다.")
    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MyCharaterListRes.class) ) } ),
//            @ApiResponse(responseCode = "400", description = "캐릭터 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @DeleteMapping("/{promiseId}")
    public ResponseEntity<?> deletePromise(
//            @Parameter @CurrentUser UserPrincipal userPrincipal
            @PathVariable Long promiseId){
        return promiseService.deletePromise(promiseId);
    }
}
