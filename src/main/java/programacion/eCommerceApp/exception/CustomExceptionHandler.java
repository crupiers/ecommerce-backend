package programacion.eCommerceApp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice //especifico que esta clase nos sirve para las excepciones del controlador
public class CustomExceptionHandler {
    //metodo de formato para mostrar las excepciones por consola
    //recibo el estado HTTP que me indica el error "web"
    //recibo una lista con los errores de consola mediante el parametro "message"
    //uso "request" para describir quién origina el error
    private Map<String, Object> buildBadRequestResponseBody(HttpStatus status, List<String> message,
                                                            WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("details", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return body;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        return new ResponseEntity<>("DATOS DUPLICADOS", HttpStatus.CONFLICT);
//    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String response = "DATOS DE ENTRADA DUPLICADOS, INTENTE CON OTROS DATOS";
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    //declaro un metodo especifico para una excepcion especifica
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //recibo la excepcion "ex" y la peticion "request"
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>(); //lista para guardar los errores

        //creamos un objeto "FieldError" que guarda un campo de error, es decir, un error concreto
        //con "getBindingResult().getFieldErrors()" recibo una lista de "FieldError"
        //así itero el "for" por cada campo de error de la excepcion
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors())
            //agrego el mensaje del campo de error a mi lista que cree
            errors.add(fieldError.getDefaultMessage());

        //una vez tengo los errores, creo la salida con el metodo pertinente de esta clase
        Map<String, Object> body = this.buildBadRequestResponseBody(HttpStatus.BAD_REQUEST, errors, request);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}