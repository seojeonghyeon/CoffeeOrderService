package com.justin.teaorderservice.modules.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOrderCountRepository extends JpaRepository<ProductOrderCount, Long>, ProductOrderCountRepositoryCustom {
    Optional<ProductOrderCount> findByMenuId(Long menuId);
}
