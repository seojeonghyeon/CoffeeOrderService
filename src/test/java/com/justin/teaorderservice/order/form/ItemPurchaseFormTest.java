package com.justin.teaorderservice.order.form;

import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
class ItemPurchaseFormTest {

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void init(){
        validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        validator.afterPropertiesSet();
    }

    @Test
    @DisplayName("Tea Order에 대한 구매 양식 검증 : Normal Case")
    void validationItemPurchaseForm(){
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                .orderQuantity(1)
                .build();
        itemOrderFormList.add(itemOrderForm);
        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .itemOrderFormList(itemOrderFormList)
                .userId(UUID.randomUUID().toString())
                .build();
        Set<ConstraintViolation<ItemPurchaseForm>> validate = validator.validate(itemPurchaseForm);
        Iterator<ConstraintViolation<ItemPurchaseForm>> iterator = validate.iterator();

        assertThat(false).isEqualTo(iterator.hasNext());
    }

    @Test
    @DisplayName("Tea Order에 대한 구매 양식 검증 : ItemOrderForm is Null")
    void validationItemOrderFormIsNull(){
        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .itemOrderFormList(null)
                .userId(UUID.randomUUID().toString())
                .build();
        Set<ConstraintViolation<ItemPurchaseForm>> validate = validator.validate(itemPurchaseForm);
        Iterator<ConstraintViolation<ItemPurchaseForm>> iterator = validate.iterator();

        assertThat(true).isEqualTo(iterator.hasNext());
        while(iterator.hasNext()){
            ConstraintViolation<ItemPurchaseForm> next = iterator.next();
            String message = next.getMessage();
            log.info("message = {}",message);
            assertThat("널이어서는 안됩니다").isEqualTo(message);
        }
    }

    @Test
    @DisplayName("Tea Order에 대한 구매 양식 검증 : orderQuantity < 0")
    void validationItemOrderFormUnderOrderQuantity(){
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                .orderQuantity(-1)
                .build();
        itemOrderFormList.add(itemOrderForm);
        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .itemOrderFormList(itemOrderFormList)
                .userId(UUID.randomUUID().toString())
                .build();
        Set<ConstraintViolation<ItemPurchaseForm>> validate = validator.validate(itemPurchaseForm);
        Iterator<ConstraintViolation<ItemPurchaseForm>> iterator = validate.iterator();

        assertThat(true).isEqualTo(iterator.hasNext());
        while(iterator.hasNext()){
            ConstraintViolation<ItemPurchaseForm> next = iterator.next();
            String message = next.getMessage();
            log.info("message = {}",message);
            assertThat("0에서 1000 사이여야 합니다").isEqualTo(message);
        }
    }

    @Test
    @DisplayName("Tea Order에 대한 구매 양식 검증 : orderQuantity > 1000")
    void validationItemOrderFormOverOrderQuantity(){
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                .orderQuantity(1001)
                .build();
        itemOrderFormList.add(itemOrderForm);
        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .itemOrderFormList(itemOrderFormList)
                .userId(UUID.randomUUID().toString())
                .build();
        Set<ConstraintViolation<ItemPurchaseForm>> validate = validator.validate(itemPurchaseForm);
        Iterator<ConstraintViolation<ItemPurchaseForm>> iterator = validate.iterator();

        assertThat(true).isEqualTo(iterator.hasNext());
        while(iterator.hasNext()){
            ConstraintViolation<ItemPurchaseForm> next = iterator.next();
            String message = next.getMessage();
            log.info("message = {}",message);
            assertThat("0에서 1000 사이여야 합니다").isEqualTo(message);
        }
    }

    @Test
    @DisplayName("Tea Order에 대한 구매 양식 검증 : orderQuantity is Null")
    void validationItemOrderFormOrderQuantityIsNull(){
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        ItemOrderForm itemOrderForm = null;
        itemOrderFormList.add(itemOrderForm);
        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .itemOrderFormList(itemOrderFormList)
                .userId(UUID.randomUUID().toString())
                .build();
        Set<ConstraintViolation<ItemPurchaseForm>> validate = validator.validate(itemPurchaseForm);
        Iterator<ConstraintViolation<ItemPurchaseForm>> iterator = validate.iterator();

        assertThat(true).isEqualTo(iterator.hasNext());
        while(iterator.hasNext()){
            ConstraintViolation<ItemPurchaseForm> next = iterator.next();
            String message = next.getMessage();
            log.info("message = {}",message);
            assertThat("널이어서는 안됩니다").isEqualTo(message);
        }
    }

}