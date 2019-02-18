package com.cetc.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/test")
public class TestResource {

    @GetMapping("/test")
    public Object test(Principal principal) {
        return "test" + principal;
    }
}
