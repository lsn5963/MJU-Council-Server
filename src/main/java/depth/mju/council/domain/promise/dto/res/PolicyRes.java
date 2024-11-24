package depth.mju.council.domain.promise.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PolicyRes {
    private Long id;
    private String title;
}