package com.example.email.service.impl;

import com.example.email.model.UserValidation;
import com.example.email.model.infra.UserRepository;
import com.example.email.model.infra.ValidationRepository;
import com.example.email.model.infra.projection.UserProjectionData;
import com.example.email.service.UserValidationService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Service
public class UserValidationImpl implements UserValidationService {

    private final UserRepository userRepository;

    private final ValidationRepository validationRepository;


    public UserValidationImpl(UserRepository userRepository, ValidationRepository validationRepository) {
        this.userRepository = userRepository;
        this.validationRepository = validationRepository;
    }

    @Transactional
    public ResponseEntity<?> postValidation(UserValidation user) {

        user.setDataTokenValidacao(LocalDateTime.now());

        validationRepository.save(user);

        checkUserCode(user);

      return  validationTimerToken(user);



    }

    @Transactional
    public ResponseEntity<?> checkUserCode(UserValidation user) {

        var query = userRepository.findByCodigo(user.getEmail());

        if (!user.getToken().equals(query.getCodigo())) {

            throw new RuntimeException("Sem Acesso");
        }

         return ResponseEntity.ok().body("Token Ok");
    }

    @Transactional
    public ResponseEntity <?> validationTimerToken(UserValidation user){

     LocalDateTime dataAtualToken = LocalDateTime.now();

     UserProjectionData dataCriacao = userRepository.findByDataValidadeToken(user.getEmail());

     LocalDateTime data = dataCriacao.getDataCriacaoToken();

     if (data == null){
         throw new RuntimeException("Nullo");
     }

      if (dataAtualToken.isAfter(dataCriacao.getDataCriacaoToken())){
            throw new RuntimeException("H2 > H1");
      }

     return ResponseEntity.ok().body("Token Valido");


    }
}
