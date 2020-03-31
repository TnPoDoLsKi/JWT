package com.exemple.jwtauth.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private UserServices userServices;


    @GetMapping("/hello")
    public Principal hello(Principal principal){
        return principal;
    }
    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user){
        userServices.save(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user) throws Exception{
        return userServices.signIn(user);
    }

}
