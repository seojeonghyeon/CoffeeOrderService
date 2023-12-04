package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.category.CategoryRepository;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.order.ProductOrderCountRepository;
import com.justin.teaorderservice.modules.menu.MenuRepository;
import com.justin.teaorderservice.modules.category.MenuCategoryRepository;
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
    @Profile({"local", "test"})
    public TestDataInit testDataInit(MenuRepository menuRepository, MemberRepository memberRepository, CategoryRepository categoryRepository, MenuCategoryRepository menuCategoryRepository, BCryptPasswordEncoder passwordEncoder, ProductOrderCountRepository productOrderCountRepository){
        return new TestDataInit(menuRepository, memberRepository, categoryRepository, menuCategoryRepository, passwordEncoder, productOrderCountRepository);
    }
}
