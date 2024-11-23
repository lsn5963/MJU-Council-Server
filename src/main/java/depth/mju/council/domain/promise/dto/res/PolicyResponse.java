package depth.mju.council.domain.promise.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PolicyResponse {
    private Long id;
    private String title;
}