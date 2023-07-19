package com.example.email.model.infra;

import com.example.email.model.User;
import com.example.email.model.infra.projection.UserProjection;
import com.example.email.model.infra.projection.UserProjectionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select codigo from user where email = :email " , nativeQuery = true )
    UserProjection findByCodigo(@Param(value = "email") String email);

    <T> Optional <T> findByEmail (String email);

    @Query(value = "select data_criacao as dataCriacaoToken from user where email = :email " , nativeQuery = true)
    UserProjectionData findByDataValidadeToken(@Param(value = "email") String email);
}
