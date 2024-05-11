package com.wolroys.wellbeing.aop;

import com.wolroys.wellbeing.domain.exception.EntityNotFoundException;
import com.wolroys.wellbeing.util.response.Violation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundExceptions(EntityNotFoundException e) {
        Violation violation = new Violation(HttpStatus.NOT_FOUND);
        violation.setMessage(e.getMessage());
        return buildResponseEntity(violation);
    }

    private ResponseEntity<Object> buildResponseEntity(Violation violation) {
        return new ResponseEntity<>(violation, violation.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessException(AccessDeniedException e) {
        Violation violation = new Violation(HttpStatus.FORBIDDEN);
        violation.setMessage("Access denied");
        return buildResponseEntity(violation);
    }
}
