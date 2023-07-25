package com.example.email.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class Handler {

    @Getter
    private List<String> erro;

    Handler(String erro){
        this.erro = Arrays.asList(erro);

    }


}
