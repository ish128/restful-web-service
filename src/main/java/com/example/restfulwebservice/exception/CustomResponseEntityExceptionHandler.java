package com.example.restfulwebservice.exception;

import com.example.restfulwebservice.user.NotFoundUserException;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Customized response entity exception handler.
 * <p> 서블릿 내에서 발생한 예외처리 담당 클래스 (cf. ErrorController 인터페이스 구현체가 WAS error page) </p>
 * ResponseEntityExceptionHandler : 예외발생 시, rest api 응답으로 처리되도록 특정예외가 등록되어 있다. response entity 커스텀
 * 가능
 */
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle response entity. 전역에러 처리용으로, @ExceptionHandler에 등록되지 않은 예외는 여기서 모두 처리한다.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handle(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(LocalDateTime.now(),
            ex.getMessage(), request.getDescription(false));
    ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
    return new ResponseEntity<>(exceptionResponse,
        responseStatus != null ? responseStatus.code() : HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle not found user response entity. NotFoundUserException 예외처리용 메소드
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(NotFoundUserException.class)
  public final ResponseEntity<Object> handleNotFoundUser(NotFoundUserException ex,
      WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(LocalDateTime.now(),
            ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(LocalDateTime.now(),
            "Validation Failed", ex.getBindingResult().toString());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}
