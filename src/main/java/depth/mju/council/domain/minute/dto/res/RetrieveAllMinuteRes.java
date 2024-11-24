package depth.mju.council.domain.minute.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RetrieveAllMinuteRes {
    @Schema(type = "integer", example = "1", description = "회의록 ID")
    private Long id;

    @Schema(type = "string", example = "관리자", description = "작성자")
    private String writer;

    @Schema(type = "string", example = "뎁스운영진회의", description = "회의록 제목")
    private String title;

    @Schema(type = "string", example = "2024-11-17", description = "작성일")
    private LocalDate date;
}
