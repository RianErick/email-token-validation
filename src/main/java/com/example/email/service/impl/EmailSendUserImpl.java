package com.example.email.service.impl;

import com.example.email.Util.Token;
import com.example.email.model.User;
import com.example.email.model.infra.UserRepository;
import com.example.email.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        user.setDataCriacaoToken(LocalDateTime.now().plusMinutes(2));

        checkEmailExist(user.getEmail());

        sendEmail(user.getEmail(),
                 "Code Validation ",
                 " Code  : " + user.getCodigo());

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

         boolean emailExist = userRepository.findByEmail(email).isPresent();

             if(emailExist) {
                 throw new RuntimeException("Email Exist");
             }

             return email;

    }



}
