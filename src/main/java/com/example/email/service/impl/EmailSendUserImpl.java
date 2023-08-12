package com.example.email.service.impl;

import com.example.email.util.ModelErro;
import com.example.email.util.Token;
import com.example.email.model.User;
import com.example.email.model.infra.UserRepository;
import com.example.email.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailSendUserImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailSendUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveEmailUser(User user){

        user.setCodigo(Token.generationCode());

        user.setDataCriacaoToken(LocalDateTime.now().plusMinutes(15));

      checkEmailExist(user.getEmail());

        sendEmail(user.getEmail(),
                 "Code Validation ",
                   "Code  : " + user.getCodigo());

        return userRepository.save(user);

    }

    @Transactional
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);

    }
    @Transactional
    public String checkEmailExist(String email) {

         boolean isEmailExist = userRepository.findByEmail(email).isPresent();

             if(isEmailExist) {
                 throw new ModelErro("Email Exist");
             }

             return email;
    }
    @Transactional
    public ResponseEntity<?> sendEmailAll(){

       List<User> userList = new ArrayList<>();

       userList = userRepository.findAll();

       if (userList.isEmpty()){
           throw new ModelErro("List is Empty");
       }

       userList.stream()
               .forEach(user -> sendEmail(user.getEmail(),
                       "Hello",
                       "Email All"));

       return ResponseEntity.ok().build();
    }


}
