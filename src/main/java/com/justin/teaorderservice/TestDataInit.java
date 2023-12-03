package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.category.Category;
import com.justin.teaorderservice.modules.category.CategoryRepository;
import com.justin.teaorderservice.modules.category.ProductCategory;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.order.ProductCount;
import com.justin.teaorderservice.modules.order.ProductCountRepository;
import com.justin.teaorderservice.modules.product.*;
import com.justin.teaorderservice.modules.category.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@RequiredArgsConstructor
public class TestDataInit {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProductCountRepository productCountRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Category drink = Category.createCategory(null,"Drink");
        ProductCategory productCategory1 = drink.addDrinkCategory();
        ProductCategory productCategory2 = drink.addDrinkCategory();
        ProductCategory productCategory3 = drink.addDrinkCategory();
        ProductCategory productCategory4 = drink.addDrinkCategory();
        ProductCategory productCategory5 = drink.addDrinkCategory();
        ProductCategory productCategory6 = drink.addDrinkCategory();

        Category coffee = Category.createCategory(drink, "Coffee");
        ProductCategory coffeeCategory1 = coffee.addDrinkCategory();
        ProductCategory coffeeCategory2 = coffee.addDrinkCategory();
        ProductCategory coffeeCategory3 = coffee.addDrinkCategory();
        ProductCategory coffeeCategory4 = coffee.addDrinkCategory();

        Category ade = Category.createCategory(drink, "Ade");
        ProductCategory adeCategory1 = ade.addDrinkCategory();


        Category teaPack = Category.createCategory(drink, "Tea");
        ProductCategory teaPackCategory1 = teaPack.addDrinkCategory();

        Coffee coffee1 = createCoffee("Americano(Hot)", 2000, 10000,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 뜨거운 물을 희석시켜 만든 음료", false, productCategory1, coffeeCategory1);


        Coffee coffee2 = createCoffee("Americano(Ice)", 2000, 10,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 찬 물을 희석시켜 만든 음료", false, productCategory2, coffeeCategory2);


        Coffee coffee3 = createCoffee("Caffe Latte(Hot)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, productCategory3, coffeeCategory3);


        Coffee coffee4 = createCoffee("Caffe Latte(Ice)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B3%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, productCategory4, coffeeCategory4);

        Ade ade1 = createAde("자몽 에이드(Ice)", 3000, 50,
                "https://cdn.paris.spl.li/wp-content/uploads/2023/06/%EC%9E%90%EB%AA%BD-1276x1280.png",
                "자몽의 과즙에 탄산 따위를 넣어 만든 음료", false, productCategory5, adeCategory1);


        Tea teaPack1 = createTeaPack("자몽 티(Hot)", 3000, 80,
                "https://cdn.paris.spl.li/wp-content/uploads/201008-%E1%84%84%E1%85%A1%E1%84%8C%E1%85%A1%E1%84%86%E1%85%A9%E1%86%BC-1280x1280.jpg",
                "자몽의 과즙에 따끈한 물 따위를 넣어 만든 음료", false, productCategory6, teaPackCategory1);

        Product product1 = productRepository.save(coffee1);
        Product product2 = productRepository.save(coffee2);
        Product product3 = productRepository.save(coffee3);
        Product product4 = productRepository.save(coffee4);
        Product product5 = productRepository.save(ade1);
        Product product6 = productRepository.save(teaPack1);

        categoryRepository.save(drink);
        categoryRepository.save(coffee);
        categoryRepository.save(ade);
        categoryRepository.save(teaPack);

        LocalDate orderDateToday = LocalDate.now();
        LocalDate orderDateTomorrow = LocalDate.now().minusDays(1);
        ProductCount coffee1ProductCount = ProductCount.createTeaOrderCount(product1, orderDateToday);
        ProductCount coffee2ProductCount = ProductCount.createTeaOrderCount(product2, orderDateToday);
        ProductCount coffee3ProductCount = ProductCount.createTeaOrderCount(product3, orderDateToday);
        ProductCount coffee4ProductCount = ProductCount.createTeaOrderCount(product4, orderDateToday);
        ProductCount ade1ProductCount = ProductCount.createTeaOrderCount(product5, orderDateToday);
        ProductCount teaPack1ProductCount = ProductCount.createTeaOrderCount(product6, orderDateToday);
        ProductCount coffee1ProductCountTomorrow = ProductCount.createTeaOrderCount(product1, orderDateTomorrow);
        ProductCount coffee2ProductCountTomorrow = ProductCount.createTeaOrderCount(product2, orderDateTomorrow);
        ProductCount coffee3ProductCountTomorrow = ProductCount.createTeaOrderCount(product3, orderDateTomorrow);
        ProductCount coffee4ProductCountTomorrow = ProductCount.createTeaOrderCount(product4, orderDateTomorrow);
        ProductCount ade1ProductCountTomorrow = ProductCount.createTeaOrderCount(product5, orderDateTomorrow);
        ProductCount teaPack1ProductCountTomorrow = ProductCount.createTeaOrderCount(product6, orderDateTomorrow);

        productCountRepository.save(coffee1ProductCount);
        productCountRepository.save(coffee2ProductCount);
        productCountRepository.save(coffee3ProductCount);
        productCountRepository.save(coffee4ProductCount);
        productCountRepository.save(ade1ProductCount);
        productCountRepository.save(teaPack1ProductCount);
        productCountRepository.save(coffee1ProductCountTomorrow);
        productCountRepository.save(coffee2ProductCountTomorrow);
        productCountRepository.save(coffee3ProductCountTomorrow);
        productCountRepository.save(coffee4ProductCountTomorrow);
        productCountRepository.save(ade1ProductCountTomorrow);
        productCountRepository.save(teaPack1ProductCountTomorrow);
    }

    private Member createMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        return Member.createUserMember(email, encryptedPwd, simpleEncryptedPwd);
    }

    private Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, ProductCategory... teaCategories){
        return Coffee.createCoffee(teaName, price, stockQuantity, teaImage,description,disabled, 1, teaCategories);
    }

    private Tea createTeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, ProductCategory... teaCategories){
        return Tea.createTea(teaName, price, stockQuantity, teaImage,description,disabled, teaCategories);
    }

    private Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, ProductCategory... teaCategories){
        return Ade.createAde(teaName, price, stockQuantity, teaImage,description,disabled, teaCategories);
    }
}
