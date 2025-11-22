package com.canbagi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF kapalı – REST API için gerekli
                .csrf(csrf -> csrf.disable())

                // Tüm endpoint'lere izin ver
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // Session kullanılmayacak (opsiyonel)
                .sessionManagement(session -> session.disable())

                // Form login, basic auth kapanıyor
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }
}
