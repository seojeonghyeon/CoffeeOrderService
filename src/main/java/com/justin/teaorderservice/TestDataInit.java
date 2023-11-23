package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.category.Category;
import com.justin.teaorderservice.modules.category.CategoryRepository;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.ordercount.OrderCount;
import com.justin.teaorderservice.modules.ordercount.OrderCountRepository;
import com.justin.teaorderservice.modules.tea.*;
import com.justin.teaorderservice.modules.teacategory.TeaCategory;
import com.justin.teaorderservice.modules.teacategory.TeaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class TestDataInit {

    private final TeaRepository teaRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final TeaCategoryRepository teaCategoryRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderCountRepository orderCountRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Category drink = Category.createCategory(null,"Drink");
        TeaCategory drinkCategory1 = drink.addTeaCategory();
        TeaCategory drinkCategory2 = drink.addTeaCategory();
        TeaCategory drinkCategory3 = drink.addTeaCategory();
        TeaCategory drinkCategory4 = drink.addTeaCategory();
        TeaCategory drinkCategory5 = drink.addTeaCategory();
        TeaCategory drinkCategory6 = drink.addTeaCategory();

        Category coffee = Category.createCategory(drink, "Coffee");
        TeaCategory coffeeCategory1 = coffee.addTeaCategory();
        TeaCategory coffeeCategory2 = coffee.addTeaCategory();
        TeaCategory coffeeCategory3 = coffee.addTeaCategory();
        TeaCategory coffeeCategory4 = coffee.addTeaCategory();

        Category ade = Category.createCategory(drink, "Ade");
        TeaCategory adeCategory1 = ade.addTeaCategory();


        Category teaPack = Category.createCategory(drink, "TeaPack");
        TeaCategory teaPackCategory1 = teaPack.addTeaCategory();

        Coffee coffee1 = createCoffee("Americano(Hot)", 2000, 10000,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 뜨거운 물을 희석시켜 만든 음료", false, drinkCategory1, coffeeCategory1);


        Coffee coffee2 = createCoffee("Americano(Ice)", 2000, 10,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 찬 물을 희석시켜 만든 음료", false, drinkCategory2, coffeeCategory2);


        Coffee coffee3 = createCoffee("Caffe Latte(Hot)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, drinkCategory3, coffeeCategory3);


        Coffee coffee4 = createCoffee("Caffe Latte(Ice)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B3%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, drinkCategory4, coffeeCategory4);

        Ade ade1 = createAde("자몽 에이드(Ice)", 3000, 50,
                "https://cdn.paris.spl.li/wp-content/uploads/2023/06/%EC%9E%90%EB%AA%BD-1276x1280.png",
                "자몽의 과즙에 탄산 따위를 넣어 만든 음료", false, drinkCategory5, adeCategory1);


        TeaPack teaPack1 = createTeaPack("자몽 티(Hot)", 3000, 80,
                "https://cdn.paris.spl.li/wp-content/uploads/201008-%E1%84%84%E1%85%A1%E1%84%8C%E1%85%A1%E1%84%86%E1%85%A9%E1%86%BC-1280x1280.jpg",
                "자몽의 과즙에 따끈한 물 따위를 넣어 만든 음료", false, drinkCategory6, teaPackCategory1);

        Tea tea1 = teaRepository.save(coffee1);
        Tea tea2 = teaRepository.save(coffee2);
        Tea tea3 = teaRepository.save(coffee3);
        Tea tea4 = teaRepository.save(coffee4);
        Tea tea5 = teaRepository.save(ade1);
        Tea tea6 = teaRepository.save(teaPack1);

        categoryRepository.save(drink);
        categoryRepository.save(coffee);
        categoryRepository.save(ade);
        categoryRepository.save(teaPack);

        LocalDate orderDateToday = LocalDate.now();
        LocalDate orderDateTomorrow = LocalDate.now().minusDays(1);
        OrderCount coffee1OrderCount = OrderCount.createTeaOrderCount(tea1, orderDateToday);
        OrderCount coffee2OrderCount = OrderCount.createTeaOrderCount(tea2, orderDateToday);
        OrderCount coffee3OrderCount = OrderCount.createTeaOrderCount(tea3, orderDateToday);
        OrderCount coffee4OrderCount = OrderCount.createTeaOrderCount(tea4, orderDateToday);
        OrderCount ade1OrderCount = OrderCount.createTeaOrderCount(tea5, orderDateToday);
        OrderCount teaPack1OrderCount = OrderCount.createTeaOrderCount(tea6, orderDateToday);
        OrderCount coffee1OrderCountTomorrow = OrderCount.createTeaOrderCount(tea1, orderDateTomorrow);
        OrderCount coffee2OrderCountTomorrow = OrderCount.createTeaOrderCount(tea2, orderDateTomorrow);
        OrderCount coffee3OrderCountTomorrow = OrderCount.createTeaOrderCount(tea3, orderDateTomorrow);
        OrderCount coffee4OrderCountTomorrow = OrderCount.createTeaOrderCount(tea4, orderDateTomorrow);
        OrderCount ade1OrderCountTomorrow = OrderCount.createTeaOrderCount(tea5, orderDateTomorrow);
        OrderCount teaPack1OrderCountTomorrow = OrderCount.createTeaOrderCount(tea6, orderDateTomorrow);

        orderCountRepository.save(coffee1OrderCount);
        orderCountRepository.save(coffee2OrderCount);
        orderCountRepository.save(coffee3OrderCount);
        orderCountRepository.save(coffee4OrderCount);
        orderCountRepository.save(ade1OrderCount);
        orderCountRepository.save(teaPack1OrderCount);
        orderCountRepository.save(coffee1OrderCountTomorrow);
        orderCountRepository.save(coffee2OrderCountTomorrow);
        orderCountRepository.save(coffee3OrderCountTomorrow);
        orderCountRepository.save(coffee4OrderCountTomorrow);
        orderCountRepository.save(ade1OrderCountTomorrow);
        orderCountRepository.save(teaPack1OrderCountTomorrow);
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
