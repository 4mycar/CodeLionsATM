package com.serdyukov.atmservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class HomeController {
    @RequestMapping("/")
    void handleFoo(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8090/swagger-ui/index.html#/");
    }
}
