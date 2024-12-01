package depth.mju.council.domain.event.controller;

import depth.mju.council.domain.event.dto.req.CreateEventReq;
import depth.mju.council.domain.event.service.EventService;
import depth.mju.council.global.config.UserPrincipal;
import depth.mju.council.global.payload.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Operation(summary = "행사 등록")
    @PostMapping()
    public ResponseEntity<ApiResult> createEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(description = "Multiaprt form-data 형식으로, 업로드할 이미지의 리스트입니다. 보낼 데이터가 없다면 빈 리스트로 전달해주세요.", required = true) @RequestPart List<MultipartFile> images,
            @Parameter(description = "Schemas의 CreateEventReq를 참고해주세요.", required = true) @Valid @RequestPart CreateEventReq createEventReq
    ) {
        eventService.createEvent(userPrincipal, images, createEventReq);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("행사가 등록되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "행사 전체 삭제")
    @DeleteMapping()
    public ResponseEntity<ApiResult> deleteAllEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        eventService.deleteAllEvent(userPrincipal);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("행사가 전체 삭제되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "행사 삭제")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResult> deleteEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(description = "삭제하고자 하는 행사의 id를 입력해주세요.", required = true) @PathVariable Long eventId
    ) {
        eventService.deleteEvent(userPrincipal, eventId);
        ApiResult apiResult = ApiResult.builder()
                .check(true)
                .message("행사가 삭제되었습니다.")
                .build();
        return ResponseEntity.ok(apiResult);
    }
}