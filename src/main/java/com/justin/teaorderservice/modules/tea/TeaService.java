package com.justin.teaorderservice.modules.tea;

import java.util.List;

public interface TeaService {
    List<Tea> findAll();
    Tea findById(Long teaId);
    void update(Long teaId, Tea tea);
}
