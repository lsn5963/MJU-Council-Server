package depth.mju.council.domain.notice.controller;

import depth.mju.council.domain.notice.dto.req.NoticeRequest;
import depth.mju.council.domain.notice.service.NoticeService;
import depth.mju.council.global.payload.ApiResult;
import depth.mju.council.global.payload.ErrorResponse;
import depth.mju.council.global.payload.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    } )
    @PostMapping()
    public ResponseEntity<ApiResult> creatNotice(
            //@CurrentUser CustomUserDetails userDetails,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 이미지의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> images,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 파일의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> files,
            @Parameter(description = "Schemas의 NoticeRequest를 참고해주세요.", required = true) @Valid @RequestPart NoticeRequest noticeRequest
    ) {
        noticeService.createNotice(images, files, noticeRequest);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("공지사항이 등록되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }
}
