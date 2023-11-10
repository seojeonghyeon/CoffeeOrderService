package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.authority.Authority;
import com.justin.teaorderservice.modules.authority.AuthorityRepository;
import com.justin.teaorderservice.modules.category.Category;
import com.justin.teaorderservice.modules.category.CategoryRepository;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.tea.*;
import com.justin.teaorderservice.modules.teacategory.TeaCategory;
import com.justin.teaorderservice.modules.teacategory.TeaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class TestDataInit {

    private final TeaRepository teaRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final TeaCategoryRepository teaCategoryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Category drink = Category.createCategory(null,"Drink");
        categoryRepository.save(drink);

        Category coffee = Category.createCategory(drink, "Coffee");
        categoryRepository.save(coffee);

        Category ade = Category.createCategory(drink, "Ade");
        categoryRepository.save(ade);

        Category teaPack = Category.createCategory(drink, "TeaPack");
        categoryRepository.save(teaPack);

        TeaCategory coffeeTeaCategory = coffee.getTeaCategories().get(0);
        TeaCategory adeTeaCategory = ade.getTeaCategories().get(0);
        TeaCategory teaPackTeaCategory = teaPack.getTeaCategories().get(0);

        Coffee coffee1 = createCoffee("Americano(Hot)", 2000, 10000,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 뜨거운 물을 희석시켜 만든 음료", false, coffeeTeaCategory);
        teaRepository.save(coffee1);

        Coffee coffee2 = createCoffee("Americano(Ice)", 2000, 10,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 찬 물을 희석시켜 만든 음료", false, coffeeTeaCategory);
        teaRepository.save(coffee2);

        Coffee coffee3 = createCoffee("Caffe Latte(Hot)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, coffeeTeaCategory);
        teaRepository.save(coffee3);

        Coffee coffee4 = createCoffee("Caffe Latte(Ice)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B3%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, coffeeTeaCategory);
        teaRepository.save(coffee4);


        Ade ade1 = createAde("자몽 에이드(Ice)", 3000, 50,
                "https://cdn.paris.spl.li/wp-content/uploads/2023/06/%EC%9E%90%EB%AA%BD-1276x1280.png",
                "자몽의 과즙에 탄산 따위를 넣어 만든 음료", false, adeTeaCategory);
        teaRepository.save(ade1);

        TeaPack teaPack1 = createTeaPack("자몽 티(Hot)", 3000, 80,
                "https://cdn.paris.spl.li/wp-content/uploads/201008-%E1%84%84%E1%85%A1%E1%84%8C%E1%85%A1%E1%84%86%E1%85%A9%E1%86%BC-1280x1280.jpg",
                "자몽의 과즙에 따끈한 물 따위를 넣어 만든 음료", false, teaPackTeaCategory);
        teaRepository.save(teaPack1);

        teaCategoryRepository.save(coffeeTeaCategory);
        teaCategoryRepository.save(adeTeaCategory);
        teaCategoryRepository.save(teaPackTeaCategory);
    }

    private Member createMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        return Member.createUserMember(email, encryptedPwd, simpleEncryptedPwd);
    }

    private Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, TeaCategory... teaCategories){
        return Coffee.createCoffee(teaName, price, stockQuantity, teaImage,description,disabled, 1, teaCategories);
    }

    private TeaPack createTeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, TeaCategory... teaCategories){
        return TeaPack.createTeaPack(teaName, price, stockQuantity, teaImage,description,disabled, teaCategories);
    }

    private Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, TeaCategory... teaCategories){
        return Ade.createAde(teaName, price, stockQuantity, teaImage,description,disabled, teaCategories);
    }
}
