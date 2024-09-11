package programacion.eCommerceApp.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NoSuchElementException.class)
  public ExceptionPayload handleNoSuchElementException(NoSuchElementException ex) {
    return new ExceptionPayload(
        ex.getMessage(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        ex.getStackTrace()
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ExceptionPayload handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    return new ExceptionPayload(
        ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        ex.getStackTrace()
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ExceptionPayload handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ExceptionPayload(
        ex.getMessage(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        ex.getStackTrace()
    );
  }

  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  @ExceptionHandler(RuntimeException.class)
  public ExceptionPayload handleRuntimeException(RuntimeException ex) {
    return new ExceptionPayload(
        ex.getMessage(),
        HttpStatus.SERVICE_UNAVAILABLE.value(),
        LocalDateTime.now(),
        ex.getStackTrace()
    );
  }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ExceptionPayload handleResponseStatusException(ResponseStatusException ex) {
        return new ExceptionPayload(
                ex.getReason(),
                ex.getStatusCode().value(),
                LocalDateTime.now(),
                ex.getStackTrace()
        );
    }

}
