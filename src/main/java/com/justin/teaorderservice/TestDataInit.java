package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.authority.Authority;
import com.justin.teaorderservice.modules.authority.AuthorityRepository;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.tea.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
public class TestDataInit {

    private final TeaRepository teaRepository;
    private final AuthorityRepository authorityRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Coffee coffee1 = createCoffee("Americano(Hot)", 2000, 10000,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 뜨거운 물을 희석시켜 만든 음료", false);
        teaRepository.save(coffee1);

        Coffee coffee2 = createCoffee("Americano(Ice)", 2000, 10,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 찬 물을 희석시켜 만든 음료", false);
        teaRepository.save(coffee2);

        Coffee coffee3 = createCoffee("Caffe Latte(Hot)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false);
        teaRepository.save(coffee3);

        Coffee coffee4 = createCoffee("Caffe Latte(Ice)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B3%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false);
        teaRepository.save(coffee4);


        Ade ade1 = createAde("자몽 에이드(Ice)", 3000, 50,
                "https://cdn.paris.spl.li/wp-content/uploads/2023/06/%EC%9E%90%EB%AA%BD-1276x1280.png",
                "자몽의 과즙에 탄산 따위를 넣어 만든 음료", false);
        teaRepository.save(ade1);

        TeaPack teaPack1 = createTeaPack("자몽 티(Hot)", 3000, 80,
                "https://cdn.paris.spl.li/wp-content/uploads/201008-%E1%84%84%E1%85%A1%E1%84%8C%E1%85%A1%E1%84%86%E1%85%A9%E1%86%BC-1280x1280.jpg",
                "자몽의 과즙에 따끈한 물 따위를 넣어 만든 음료", false);
        teaRepository.save(teaPack1);


        Authority authority1 = createAuthority("ADMIN");
        authorityRepository.save(authority1);

        Authority authority2 = createAuthority("MANAGER");
        authorityRepository.save(authority2);

        Authority authority3 = createAuthority("USER");
        authorityRepository.save(authority3);


        Member member = createMember("seojeonghyeon0630@gmail.com", passwordEncoder.encode("SEOjh1234!"), passwordEncoder.encode("1234"));
        memberRepository.save(member);
    }

    private Member createMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        Member member = Member.createUserMember(email, encryptedPwd, simpleEncryptedPwd);
        return member;
    }

    private Authority createAuthority(String role){
        Authority authority = Authority.creatAuthority(role);
        return authority;
    }

    private Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        Coffee coffee = Coffee.createCoffee(teaName, price, stockQuantity, teaImage,description,disabled, 1);
        return coffee;
    }

    private TeaPack createTeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        TeaPack teaPack = TeaPack.createTeaPack(teaName, price, stockQuantity, teaImage,description,disabled);
        return teaPack;
    }

    private Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        Ade ade = Ade.createAde(teaName, price, stockQuantity, teaImage,description,disabled);
        return ade;
    }
}
