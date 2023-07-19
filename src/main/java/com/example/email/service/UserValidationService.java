package com.example.email.service;

import com.example.email.model.UserValidation;
import org.springframework.http.ResponseEntity;

public interface UserValidationService {

    ResponseEntity <?> postValidation(UserValidation userValidation);
}
