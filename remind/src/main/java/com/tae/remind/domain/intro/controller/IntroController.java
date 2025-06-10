package com.tae.remind.domain.intro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntroController {

     @GetMapping("/api/hello")
    public String hello() {
        return "젠킨스 테스3";
    }
}
