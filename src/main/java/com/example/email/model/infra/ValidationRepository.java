package com.example.email.model.infra;

import com.example.email.model.UserValidation;
import com.example.email.model.infra.projection.GetCodigoValidadorToken;
import com.example.email.model.infra.projection.ValidationProjetion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ValidationRepository extends JpaRepository<UserValidation,Long> {

   @Query(value = "select data_validacao from validation where email = :email" , nativeQuery = true)
   ValidationProjetion findByDataRequest(@Param(value = "email") String email);

   <T> Optional <T> findByEmail (String email);
   @Query(value = "select codigo_validador_token as codigoValidadorToken from validation where email = :email" , nativeQuery = true)
   GetCodigoValidadorToken findByCodigoValidadorToken(@Param(value = "email") String email);


}
