package com.example.email.model.infra;

import com.example.email.model.UserValidation;
import com.example.email.model.infra.projection.ValidationProjetion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ValidationRepository extends JpaRepository<UserValidation,Long> {

   @Query(value = "select data_validacao from validation where email = :email" , nativeQuery = true)
   ValidationProjetion findByDataRequest(@Param(value = "email") String email);
}
