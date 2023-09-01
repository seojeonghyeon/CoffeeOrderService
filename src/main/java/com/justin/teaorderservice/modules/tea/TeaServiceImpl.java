package com.justin.teaorderservice.modules.tea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeaServiceImpl implements TeaService {

    private final TeaRepository teaRepository;

    @Override
    public List<Tea> findAll() {
        return teaRepository.findAll();
    }

    @Override
    public Tea findById(Long id) {
        return teaRepository.findById(id);
    }

    @Transactional
    @Override
    public void update(Long teaId, Tea tea) {
        teaRepository.update(teaId, tea);
    }
}
