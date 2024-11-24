package depth.mju.council.domain.promise.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePromiseReq {
    @Schema(type = "string", example = "재수강 학점 A0 확대", description="공약명")
    private String title;
    @Schema(type = "string", example = "블라블라블라", description="공약 내용")
    private String content;
    @Schema(type = "integer", example = "0", description="공약 진행 정도 0-> 진행x, 1-> 진행중, 2-> 진행완료")
    private Long progress;
}
