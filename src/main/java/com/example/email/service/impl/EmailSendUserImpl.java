package com.example.email.service.impl;

import com.example.email.model.User;
import com.example.email.model.infra.UserRepository;
import com.example.email.model.infra.projection.GetEmailProjection;
import com.example.email.service.UserService;
import com.example.email.util.ModelErro;
import com.example.email.util.Token;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailSendUserImpl implements UserService {


    private UserService userService;

    private final UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailSendUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveEmailUser(User user) {

        user.setCodigo(Token.generationCode());
        user.setDataCriacaoToken(LocalDateTime.now().plusMinutes(15));

        checkEmailExist(user.getEmail());

        sendEmail(user.getEmail(), "Code Validation ", "Code  : " + user.getCodigo());

        return userRepository.save(user);

    }

    @Async
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
        if (isEmailExist) {
            throw new ModelErro("Email Exist");
        }
        return email;
    }

    @Async
    public void sendEmailAll() {
        List<GetEmailProjection> listEmail = userRepository.findAllEmail();
        if (listEmail.isEmpty()) {
            throw new ModelErro("List is Empty");
        }
        listEmail.stream()
                .forEach(user -> sendEmail
                        (user.getEmail(), "Hello", "Email All"));
    }
}
