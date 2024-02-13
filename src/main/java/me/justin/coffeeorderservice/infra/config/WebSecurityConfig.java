package me.justin.coffeeorderservice.infra.config;

import me.justin.coffeeorderservice.infra.auth.JwtAccessDeniedHandler;
import me.justin.coffeeorderservice.infra.auth.JwtAuthenticationEntryPoint;
import me.justin.coffeeorderservice.infra.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Profile(value = {"test","local","dev","stg","prd"})
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final CorsFilter corsFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/image/**").permitAll()
                .requestMatchers("/file/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/resources/**").permitAll()

                /**
                 * Swagger
                 */
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger/**").permitAll()

                /**
                 * Docs
                 */
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/docs/*").permitAll()
                .requestMatchers("/v2/api-docs").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
//
//


                /**
                 * API
                 *
                 * URI : /api/order/login
                 * DESCRIPTION : Order Login
                 *
                 * URI : /api/order/members/add
                 * DESCRIPTION : Add USER Member
                 *
                 * * URI : /api/order/menus/**
                 * * DESCRIPTION : Search menus
                 */
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/order/login").permitAll()
                .requestMatchers("/api/order/members/add").permitAll()
                .requestMatchers("/api/order/menus/**").permitAll()
                .requestMatchers("/api/order/categories/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
