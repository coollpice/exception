package hello.exception.api;

import hello.exception.customException.UserException;
import hello.exception.exhandler.ErrorResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionControllerV2 {

    /**
     * @ExceptionHandler 는 정의된 컨트롤로 안에서만 동작한다.
     */

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


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello" + id);
    }


    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }
}
