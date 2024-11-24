package depth.mju.council.domain.promise.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PromiseRes {
    @Schema(type = "integer", example = "1", description = "공약 ID")
    private Long id;
    @Schema(type = "string", example = "재수강 학점 A0 확대", description = "공약 제목")
    private String title;

    @Schema(type = "string", example = "재수강 시 취득 가능한 학점을 A0로 확대하겠습니다", description = "공약 내용")
    private String content;

    @Schema(type = "integer", example = "0", description = "공약 진행 정도 (0: 진행x, 1: 진행중, 2: 진행완료)")
    private Long progress;
}
