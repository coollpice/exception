package hello.exception.exhandler.advice;

import hello.exception.customException.UserException;
import hello.exception.exhandler.ErrorResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통적으로 에러를 처리하기 위한 ControllerAdvice
 * @RestControllerAdvice 속성.
 * annotaion - 어노테이션 을 확인하여 해당 어노테이션이 붙은 클래스에 적용
 * "org.example.pac" - 패키지 경로를 입력하여 해당 하위 패키지 컨트롤러에 적용
 * assignableTypes  = {**.class , *.class} - 컨트롤러 클래스를 입력하여 적용
 */

@Slf4j
@RestControllerAdvice(basePackages = {"hello.exception.api"})
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 상태코드를 지정하지않으면, 정상적으로 예외처리를 한 경우 이기떄문에 200 코드가 반환된다.
    @ExceptionHandler(IllegalArgumentException.class) // 여러개의 Exception 들을 "," 를 사용하여 정의 할 수 있다.
    public ErrorResultDto illegalArgumentExceptionResolver(IllegalArgumentException e) { // 여러개의 Exception 을 사용할 경우, 해당 Exception 들의 상위 Exception 을 parameter 로 받아야한다.
        return new ErrorResultDto("BAD", e.getMessage());
    }

    @ExceptionHandler   // 생략 시 해당 메서드의 함수타입의 예외로 인식함. ▼
    public ResponseEntity<ErrorResultDto> userExceptionHandler(UserException e) {
        return new ResponseEntity<>(new ErrorResultDto("USER-EX", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResultDto exceptionResolver(Exception e) { // 최상위 Exception 을 Catch 하므로 잡히지 않은 예외들을 처리할 수 있다.
        return new ErrorResultDto("EX", e.getMessage());
    }

}
