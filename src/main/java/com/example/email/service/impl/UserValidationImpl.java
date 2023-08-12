package com.example.email.service.impl;

import com.example.email.model.UserValidation;
import com.example.email.model.infra.UserRepository;
import com.example.email.model.infra.ValidationRepository;
import com.example.email.model.infra.projection.UserProjectionData;
import com.example.email.service.UserValidationService;
import com.example.email.util.ModelErro;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
        user.setCodigoValidadorToken(0);

         checkUserCode(user);

         validationTimerToken(user);

      boolean isEmailExist = validationRepository.findByEmail(user.getEmail()).isPresent();

         if (isEmailExist) {
              user = (UserValidation) validationRepository
                               .findByEmail(user.getEmail()).get();
        }

        verificaTokenJaValidado(user);

        user.setCodigoValidadorToken(1);

        validationRepository.save(user);

       return ResponseEntity.ok().body("Token Ok");

    }

    @Transactional
    public void checkUserCode(UserValidation user) {

        var query = userRepository.findByCodigo(user.getEmail());

        if (!user.getToken().equals(query.getCodigo())) {

            throw new ModelErro("Sem Acesso");
        }
    }

    @Transactional
    public void validationTimerToken(UserValidation user){

     LocalDateTime dataAtualToken = LocalDateTime.now();

     UserProjectionData dataCriacao = userRepository.findByDataValidadeToken(user.getEmail());

       if (dataAtualToken.isAfter(dataCriacao.getDataCriacaoToken())){
            throw new ModelErro("Token Expirado");
       }
    }

    @Transactional
    public Integer verificaTokenJaValidado(UserValidation user){

        if (user.getCodigoValidadorToken().equals(1)){
            throw new ModelErro("Esse Codigo ja foi validado com sucesso");

        }
         return user.getCodigoValidadorToken();
    }
}


