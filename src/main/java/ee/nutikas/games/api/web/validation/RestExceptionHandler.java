package ee.nutikas.games.api.web.validation;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class RestExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new ArrayList<InvalidFieldError>();
        var response = new ErrorResponse();
        response.setMessage("INVALID_FIELDS");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError)error).getField();
            errors.add(InvalidFieldError.builder().fieldName(fieldName).message(error.getDefaultMessage()).build());
        });
        response.setDetails(errors);
        return response;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DisabledException.class)
    public ErrorResponse handleDisabledException(DisabledException exception) {
        var response = new ErrorResponse();
        response.setMessage("User is disabled");
        return response;
    }

}
