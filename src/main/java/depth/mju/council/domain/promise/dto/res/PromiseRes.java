package depth.mju.council.domain.promise.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PromiseRes {
    private Long id;
    private String title;
    private String content;
    private Long progress;
}
