package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.member.AuthorityRepository;
import com.justin.teaorderservice.modules.tea.TeaRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.TimeZone;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(TeaRepository teaRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder passwordEncoder){
        return new TestDataInit(teaRepository, authorityRepository, passwordEncoder);
    }
}
