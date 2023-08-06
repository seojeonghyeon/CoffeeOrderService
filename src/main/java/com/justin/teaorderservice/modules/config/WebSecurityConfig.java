package com.justin.teaorderservice.modules.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests)
                        -> requests
                        .requestMatchers(
                                "/",
                                "/home",
                                "/order/v1/members/add",
                                "/css/**",
                                "/*.ico",
                                "/error",
                                "/login",
                                "/logout",
                                "/index.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form)
                        -> form
                        .loginPage("/login")
                        .permitAll()
                ).logout((logout)
                        -> logout
                        .permitAll()
                );

        return http.build();
    }
}
