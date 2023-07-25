package com.example.email.api.controller;

import com.example.email.model.UserValidation;
import com.example.email.service.UserValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserValidationController {

  private UserValidationService userValidationService;

    public UserValidationController(UserValidationService userValidationService) {
        this.userValidationService = userValidationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateCode(@RequestBody UserValidation userValidation) {

       return userValidationService.postValidation(userValidation);

    }


}
