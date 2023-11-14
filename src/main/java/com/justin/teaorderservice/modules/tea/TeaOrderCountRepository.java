package com.justin.teaorderservice.modules.tea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeaOrderCountRepository extends JpaRepository<TeaOrderCount, Long> {
    Optional<TeaOrderCount> findByTeaId(Long teaId);
}
