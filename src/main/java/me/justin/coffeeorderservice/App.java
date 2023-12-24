package me.justin.coffeeorderservice;

import lombok.extern.slf4j.Slf4j;
import me.justin.coffeeorderservice.modules.category.CategoryRepository;
import me.justin.coffeeorderservice.modules.category.MenuCategoryRepository;
import me.justin.coffeeorderservice.modules.member.MemberRepository;
import me.justin.coffeeorderservice.modules.menu.MenuRepository;
import me.justin.coffeeorderservice.modules.order.ProductOrderCountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@EnableAsync
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
