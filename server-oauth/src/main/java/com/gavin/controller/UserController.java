package com.gavin.controller;

import com.gavin.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        principal.getName();
        return principal;
    }
}
