package com.example.email.service;

import com.example.email.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User saveEmailUser(User user);

    void sendEmailAll() ;
}
