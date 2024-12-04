package depth.mju.council.domain.event.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ModifyEventReq {
    @Schema(type = "String", example = "2024년 명지대학교 축제", description = "행사의 제목")
    @NotBlank
    public String title;

    @Schema(type = "String", example = "2024년 명지대학교 축제를 실시합니다.", description = "행사의 내용")
    @NotBlank
    public String content;

    @Schema(type = "LocalDate", example = "2024-11-17", description = "행사의 시작일자")
    @NotNull
    public LocalDate startDate;

    @Schema(type = "LocalDate", example = "2024-11-24", description = "행사의 종료일자")
    @NotNull
    public LocalDate endDate;

    @Schema(type = "List<Integer>", example = "[1, 2, 3]", description = "행사 수정 시 지우고자 하는 이미지의 리스트")
    public List<Integer> deleteImages;
}
