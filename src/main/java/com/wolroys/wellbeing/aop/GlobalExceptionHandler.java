package com.wolroys.wellbeing.aop;

import com.wolroys.wellbeing.domain.exception.EntityNotFoundException;
import com.wolroys.wellbeing.util.response.Violation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundExceptions(EntityNotFoundException e) {
        Violation violation = new Violation(HttpStatus.NOT_FOUND);
        violation.setMessage(e.getMessage());
        return buildResponseEntity(violation);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessException(AccessDeniedException e) {
        Violation violation = new Violation(HttpStatus.FORBIDDEN);
        violation.setMessage("Access denied");
        return buildResponseEntity(violation);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                HttpStatus.BAD_REQUEST, e.getMessage(), e)
                ).toList();

        return new ResponseEntity<>(violations, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public List<Violation> onMethodArgumentNotValidException(
//            MethodArgumentNotValidException e
//    ) {
//        return e.getBindingResult().getFieldErrors().stream()
//                .map(error -> new Violation(HttpStatus.BAD_REQUEST, error.getDefaultMessage(), e))
//                .toList();
//    }

    private ResponseEntity<Object> buildResponseEntity(Violation violation) {
        return new ResponseEntity<>(violation, violation.getStatus());
    }
}
