package depth.mju.council.domain.promise.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PolicyRes {
    @Schema(type = "integer", example = "1", description = "공약 ID")
    private Long id;
    @Schema(type = "string", example = "재수강 학점 A0 확대", description = "공약 제목")
    private String title;
}