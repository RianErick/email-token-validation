package com.example.email.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exception {
    @ExceptionHandler(ModelErro.class)
    public Handler handler (ModelErro modelErro){
        String erro = modelErro.getMessage();

        return new Handler(erro);
    }
}
