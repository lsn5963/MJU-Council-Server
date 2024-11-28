package depth.mju.council.domain.user.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterReq {

    @NotNull(message = "ID(username)을 입력해주세요")
    @Size(min = 5, message = "ID(username)은 5자 이상이어야 합니다")
    @Schema(type = "String", example = "si14444", description ="로그인 ID")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요")
    @Size(min = 5, message = "비밀번호는 5자 이상이어야 합니다")
    @Schema(type = "String", example = "pw44441", description ="로그인 비밀번호")
    private String password;

}
