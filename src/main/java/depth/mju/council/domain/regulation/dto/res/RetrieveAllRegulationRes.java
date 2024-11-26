package depth.mju.council.domain.regulation.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RetrieveAllRegulationRes {
    @Schema(type = "integer", example = "1", description = "배너아이디")
    private Long id;
    @Schema(type = "string", example = "adsasd123123", description = "배너url")
    private String imgUrl;
    @Schema(type = "LocalDate", example = "2024-12-23", description = "배너만든시간")
    private LocalDate date;
}
