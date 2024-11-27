package depth.mju.council.domain.notice.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NoticeRequest {

    @Schema(type = "String", example = "2024학년도 2학기 강의평가 실시 안내", description = "공지사항의 제목")
    @NotBlank
    public String title;

    @Schema(type = "String", example = "2024학년도 2학기 강의평가 실시를 안내합니다.", description = "공지사항의 내용")
    @NotBlank
    public String content;
}
