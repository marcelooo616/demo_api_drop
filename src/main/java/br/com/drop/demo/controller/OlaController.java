package br.com.drop.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class OlaController {



    @GetMapping("/login")
    public String olaMundo(){
        return "ola mundo";
    }
}
