package com.justin.teaorderservice.modules.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCountRepository extends JpaRepository<ProductCount, Long>, ProductCountRepositoryCustom {
    Optional<ProductCount> findByProductId(Long ProductId);
}
