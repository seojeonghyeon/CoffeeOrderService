package com.justin.teaorderservice.modules.teacategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeaCategoryRepository extends JpaRepository<TeaCategory, Long> {
}
