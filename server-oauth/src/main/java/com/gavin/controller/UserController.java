package com.gavin.controller;

import com.gavin.entity.User;
import com.gavin.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/user")
    public String user(Principal principal) {
        System.out.println("--------" + principal.getName());
        return principal.getName();
    }

    @PreAuthorize(value = "hasAuthority('AUTH_USER_WRITE')")
    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }
}
