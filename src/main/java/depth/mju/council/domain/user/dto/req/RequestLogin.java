package depth.mju.council.domain.user.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

    @NotNull(message = "ID(username)을 입력해주세요")
    @Size(min = 5, message = "ID(username)은 5자 이상이어야 합니다")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요")
    @Size(min = 5, message = "비밀번호는 5자 이상이어야 합니다")
    private String password;
}
