package com.prod.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class DevWebConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        httpSecurity.formLogin(c->c.disable());
        httpSecurity.httpBasic(c->c.disable());
        httpSecurity.authorizeHttpRequests(any-> any.anyRequest().permitAll());
        httpSecurity.cors(c->c.disable());
        httpSecurity.csrf(c->c.disable());
        return httpSecurity.build();
    }
}
