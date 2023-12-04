package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.category.Category;
import com.justin.teaorderservice.modules.category.CategoryRepository;
import com.justin.teaorderservice.modules.category.MenuCategory;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.order.ProductOrderCount;
import com.justin.teaorderservice.modules.order.ProductOrderCountRepository;
import com.justin.teaorderservice.modules.menu.*;
import com.justin.teaorderservice.modules.category.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@RequiredArgsConstructor
public class TestDataInit {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProductOrderCountRepository productOrderCountRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Category drink = Category.createCategory(null,"Drink");
        MenuCategory menuCategory1 = drink.addMenuCategory();
        MenuCategory menuCategory2 = drink.addMenuCategory();
        MenuCategory menuCategory3 = drink.addMenuCategory();
        MenuCategory menuCategory4 = drink.addMenuCategory();
        MenuCategory menuCategory5 = drink.addMenuCategory();
        MenuCategory menuCategory6 = drink.addMenuCategory();

        Category coffee = Category.createCategory(drink, "Coffee");
        MenuCategory coffeeCategory1 = coffee.addMenuCategory();
        MenuCategory coffeeCategory2 = coffee.addMenuCategory();
        MenuCategory coffeeCategory3 = coffee.addMenuCategory();
        MenuCategory coffeeCategory4 = coffee.addMenuCategory();

        Category ade = Category.createCategory(drink, "Ade");
        MenuCategory adeCategory1 = ade.addMenuCategory();


        Category teaPack = Category.createCategory(drink, "Tea");
        MenuCategory teaPackCategory1 = teaPack.addMenuCategory();

        Coffee coffee1 = createCoffee("Americano(Hot)", 2000, 10000,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 뜨거운 물을 희석시켜 만든 음료", false, menuCategory1, coffeeCategory1);


        Coffee coffee2 = createCoffee("Americano(Ice)", 2000, 10,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 찬 물을 희석시켜 만든 음료", false, menuCategory2, coffeeCategory2);


        Coffee coffee3 = createCoffee("Caffe Latte(Hot)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, menuCategory3, coffeeCategory3);


        Coffee coffee4 = createCoffee("Caffe Latte(Ice)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B3%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료", false, menuCategory4, coffeeCategory4);

        Ade ade1 = createAde("자몽 에이드(Ice)", 3000, 50,
                "https://cdn.paris.spl.li/wp-content/uploads/2023/06/%EC%9E%90%EB%AA%BD-1276x1280.png",
                "자몽의 과즙에 탄산 따위를 넣어 만든 음료", false, menuCategory5, adeCategory1);


        Tea teaPack1 = createTeaPack("자몽 티(Hot)", 3000, 80,
                "https://cdn.paris.spl.li/wp-content/uploads/201008-%E1%84%84%E1%85%A1%E1%84%8C%E1%85%A1%E1%84%86%E1%85%A9%E1%86%BC-1280x1280.jpg",
                "자몽의 과즙에 따끈한 물 따위를 넣어 만든 음료", false, menuCategory6, teaPackCategory1);

        Menu menu1 = menuRepository.save(coffee1);
        Menu menu2 = menuRepository.save(coffee2);
        Menu menu3 = menuRepository.save(coffee3);
        Menu menu4 = menuRepository.save(coffee4);
        Menu menu5 = menuRepository.save(ade1);
        Menu menu6 = menuRepository.save(teaPack1);

        categoryRepository.save(drink);
        categoryRepository.save(coffee);
        categoryRepository.save(ade);
        categoryRepository.save(teaPack);

        LocalDate orderDateToday = LocalDate.now();
        LocalDate orderDateTomorrow = LocalDate.now().minusDays(1);
        ProductOrderCount coffee1ProductOrderCount = ProductOrderCount.createTeaOrderCount(menu1, orderDateToday);
        ProductOrderCount coffee2ProductOrderCount = ProductOrderCount.createTeaOrderCount(menu2, orderDateToday);
        ProductOrderCount coffee3ProductOrderCount = ProductOrderCount.createTeaOrderCount(menu3, orderDateToday);
        ProductOrderCount coffee4ProductOrderCount = ProductOrderCount.createTeaOrderCount(menu4, orderDateToday);
        ProductOrderCount ade1ProductOrderCount = ProductOrderCount.createTeaOrderCount(menu5, orderDateToday);
        ProductOrderCount teaPack1ProductOrderCount = ProductOrderCount.createTeaOrderCount(menu6, orderDateToday);
        ProductOrderCount coffee1ProductOrderCountTomorrow = ProductOrderCount.createTeaOrderCount(menu1, orderDateTomorrow);
        ProductOrderCount coffee2ProductOrderCountTomorrow = ProductOrderCount.createTeaOrderCount(menu2, orderDateTomorrow);
        ProductOrderCount coffee3ProductOrderCountTomorrow = ProductOrderCount.createTeaOrderCount(menu3, orderDateTomorrow);
        ProductOrderCount coffee4ProductOrderCountTomorrow = ProductOrderCount.createTeaOrderCount(menu4, orderDateTomorrow);
        ProductOrderCount ade1ProductOrderCountTomorrow = ProductOrderCount.createTeaOrderCount(menu5, orderDateTomorrow);
        ProductOrderCount teaPack1ProductOrderCountTomorrow = ProductOrderCount.createTeaOrderCount(menu6, orderDateTomorrow);

        productOrderCountRepository.save(coffee1ProductOrderCount);
        productOrderCountRepository.save(coffee2ProductOrderCount);
        productOrderCountRepository.save(coffee3ProductOrderCount);
        productOrderCountRepository.save(coffee4ProductOrderCount);
        productOrderCountRepository.save(ade1ProductOrderCount);
        productOrderCountRepository.save(teaPack1ProductOrderCount);
        productOrderCountRepository.save(coffee1ProductOrderCountTomorrow);
        productOrderCountRepository.save(coffee2ProductOrderCountTomorrow);
        productOrderCountRepository.save(coffee3ProductOrderCountTomorrow);
        productOrderCountRepository.save(coffee4ProductOrderCountTomorrow);
        productOrderCountRepository.save(ade1ProductOrderCountTomorrow);
        productOrderCountRepository.save(teaPack1ProductOrderCountTomorrow);
    }

    private Member createMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        return Member.createUserMember(email, encryptedPwd, simpleEncryptedPwd);
    }

    private Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, MenuCategory... teaCategories){
        return Coffee.createCoffee(teaName, price, stockQuantity, teaImage,description,disabled, 1, teaCategories);
    }

    private Tea createTeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, MenuCategory... teaCategories){
        return Tea.createTea(teaName, price, stockQuantity, teaImage,description,disabled, teaCategories);
    }

    private Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, MenuCategory... teaCategories){
        return Ade.createAde(teaName, price, stockQuantity, teaImage,description,disabled, teaCategories);
    }
}
