package com.ae.authentication.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptUtil {

    @Bean
    public BCryptPasswordEncoder providerPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
