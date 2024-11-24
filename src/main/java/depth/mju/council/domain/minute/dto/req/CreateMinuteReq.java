package depth.mju.council.domain.minute.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateMinuteReq {
    @Schema(type = "string", example = "뎁스 운영진 회의", description="회의 제목")
    private String title;
    @Schema(type = "string", example = "회의했어욥", description="회의 내용")
    private String content;
}
