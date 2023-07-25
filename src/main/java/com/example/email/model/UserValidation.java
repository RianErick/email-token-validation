package com.example.email.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "validation")
public class UserValidation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer token;

  private String email;

  @Column(name = "data_validacao")
  private LocalDateTime dataTokenValidacao;

  @Column(name = "codigo_validador_token")
  private Integer codigoValidadorToken ;

}
