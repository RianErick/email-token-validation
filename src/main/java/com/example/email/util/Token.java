package com.example.email.util;

import java.security.SecureRandom;

public class Token {

    public static Integer generationCode() {

        var length = 6;

        SecureRandom secureRandom = new SecureRandom();

        StringBuilder key = new StringBuilder();

        var numbers = "0123456789";

        for (int i = 0; i < length; i++) {

            int index = secureRandom.nextInt(numbers.length());

            char code = numbers.charAt(index);

            key.append(code);

        }
        String codigoString = key.toString();

        Integer codigoInteiro = Integer.parseInt(codigoString);

        System.out.println(codigoInteiro);

        return codigoInteiro;

    }

}
