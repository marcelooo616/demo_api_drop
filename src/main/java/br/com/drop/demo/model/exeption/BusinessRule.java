package br.com.drop.demo.model.exeption;


import org.springframework.http.HttpStatus;

public class BusinessRule extends RuntimeException {

    public BusinessRule(HttpStatus httpStatus, String message) {
        super(message);
    }
}
