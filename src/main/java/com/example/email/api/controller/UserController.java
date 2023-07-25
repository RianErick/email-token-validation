package com.example.email.api.controller;

import com.example.email.model.User;
import com.example.email.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/user")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("send-email")
    public ResponseEntity<?> sendEmailForPage(@RequestBody User user) {

        if (user == null)
             return ResponseEntity.badRequest().body("User Is Empty");

       userService.saveEmailUser(user);

        return ResponseEntity.status(201)
                    .body("Email Send With Code Auth");

    }


}
