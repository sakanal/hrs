package com.sakanal.service.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

public class PasswordUtils {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean matches(String rawPassword,String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword,encodedPassword);
    }
}
