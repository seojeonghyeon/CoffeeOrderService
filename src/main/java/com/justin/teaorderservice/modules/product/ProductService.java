package com.justin.teaorderservice.modules.product;

import com.justin.teaorderservice.modules.order.ProductCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private static final Integer POPULAR_DRINK_NUMBER = 3;
    private static final Integer POPULAR_DRINK_DURING_DAYS = 7;

    private final ProductRepository productRepository;
    private final ProductCountRepository productCountRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<ProductSearchDto> search(ProductSearchCondition productSearchCondition, Pageable pageable){
        return productRepository.search(productSearchCondition, pageable);
    }

    public List<PopularProductDto> searchPopularProducts(){
        LocalDate nowDate = LocalDate.now();
        List<PopularProductDto> popularProductDtos = productRepository.searchPopularDrink(POPULAR_DRINK_NUMBER, nowDate.minusDays(POPULAR_DRINK_DURING_DAYS), nowDate);
        return popularProductDtos;
    }
}
