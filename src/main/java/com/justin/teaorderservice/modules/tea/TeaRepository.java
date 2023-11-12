package com.justin.teaorderservice.modules.tea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TeaRepository extends JpaRepository<Tea, Long>, TeaRepositoryCustom {
}
