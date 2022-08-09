package hello.exception.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST , reason = "error.bad") // 상태코드를 400으로 변경  ( properties 의 값을 사용할 수 있다. )
public class BadRequestException extends RuntimeException{

}
