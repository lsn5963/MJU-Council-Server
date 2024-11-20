package depth.mju.council.global.error;

import depth.mju.council.global.payload.ErrorCode;
import lombok.Getter;

@Getter
public class DefaultNullPointerException extends NullPointerException{

    private ErrorCode errorCode;
    private String message;

    public DefaultNullPointerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public DefaultNullPointerException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = message;
    }
}