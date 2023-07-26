package com.justin.teaorderservice.modules.tea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeaServiceImpl implements TeaService {

    private final TeaRepository teaRepository;

    @Override
    public List<Tea> findAll() {
        return teaRepository.findAll();
    }

    @Override
    public Tea findById(Long teaId) {
        return teaRepository.findById(teaId);
    }

    @Override
    public void update(Long teaId, Tea tea) {
        teaRepository.update(teaId, tea);
    }
}
