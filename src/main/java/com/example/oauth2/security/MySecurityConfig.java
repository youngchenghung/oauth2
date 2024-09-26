package com.example.oauth2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            // 設定 HttpSecurity
            .authorizeHttpRequests(request -> request

            // 允許所有人訪問首頁
            .anyRequest().authenticated()
            )

            // 表單登入
            .formLogin(Customizer.withDefaults())

            // OAuth2 登入
            .oauth2Login(Customizer.withDefaults())

            .build();
    }

}