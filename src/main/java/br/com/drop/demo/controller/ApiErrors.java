package br.com.drop.demo.controller;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {


    @Getter
    private List<String> errors;

    public ApiErrors(String  messsageError){
        this.errors = Arrays.asList(messsageError);
    }
}
