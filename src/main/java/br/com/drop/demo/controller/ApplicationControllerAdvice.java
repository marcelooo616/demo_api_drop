package br.com.drop.demo.controller;


import br.com.drop.demo.model.exeption.BusinessRule;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessRule.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(BusinessRule e){
        String messageError = e.getMessage();
        return new ApiErrors(messageError);
    }
}
