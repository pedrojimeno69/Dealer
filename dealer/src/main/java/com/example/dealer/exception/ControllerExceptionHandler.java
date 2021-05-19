package com.example.dealer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CarFeatureNotFoundException.class)
    public ResponseEntity<Object> handlerCarFeatureNotFoundException(CarFeatureNotFoundException ex, WebRequest request){

        Map<String, Object> body = createBody(ex.getMessage(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyNotFoundQueryException.class)
    public ResponseEntity<Object> handlerPropertyNotFoundQueryException(PropertyNotFoundQueryException ex, WebRequest request){

        Map<String, Object> body = createBody(ex.getMessage(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenerateExcelException.class)
    public ResponseEntity<Object> handlerGenerateExcelException(GenerateExcelException ex, WebRequest request){

        Map<String, Object> body = createBody(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map <String, Object> createBody(String msg, HttpStatus httpStatus){
        Map<String, Object> body = new HashMap<>();
        body.put("Date", LocalDateTime.now());
        body.put("Message",msg);
        body.put("Code", httpStatus.value());
        return body;
    }
}
