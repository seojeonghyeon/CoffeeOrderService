package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.authority.AuthorityRepository;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.tea.TeaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(TeaRepository teaRepository, AuthorityRepository authorityRepository, MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder){
        return new TestDataInit(teaRepository, authorityRepository, memberRepository, passwordEncoder);
    }
}
