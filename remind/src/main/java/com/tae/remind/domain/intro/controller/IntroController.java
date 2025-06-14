package com.tae.remind.domain.intro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntroController {

     @GetMapping("/api/hello")
    public String hello() {
        return "배포 자동화 완료";
    }
}
