package com.cetc.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 这里用户的获取卸载资源服务器上面，主要是为了区分
 * 当然认证服务器也可以作为资源服务器
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @GetMapping("/me")
    public Principal me(Principal principal) {
        return principal;
    }
}
