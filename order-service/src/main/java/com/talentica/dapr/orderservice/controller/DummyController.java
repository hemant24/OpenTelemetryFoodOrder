package com.talentica.dapr.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingMatrixVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@RestController
@RequestMapping(value = "/dummy")
@Slf4j
public class DummyController {

    @GetMapping("/log")
    public String root(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.error(String.format("Hello %s!!", name));
        log.debug("Debugging log");
        log.info("Info log");
        log.warn("Hey, This is a warning!");
        log.error("Oops! We have an Error. OK");
        return String.format("Hello %s!!", name);
    }

    @GetMapping("/error")
    public String error(@RequestParam(value = "name", defaultValue = "World") String name) throws Exception {
        throw new Exception("Error test");
    }
    @GetMapping("/error/random")
    public String randomError(@RequestParam(value = "name", defaultValue = "World") String name) throws Exception {
        int random = (int)(Math.random()* 10);

        if(random <= 6){
            throw new MissingServletRequestParameterException("dummy_name", "dummy_value");
        }else if(random > 6 && random <=8){
            throw new RuntimeException("Random Error test");
        }else{
            return "Hello " + name;
        }
    }
}
