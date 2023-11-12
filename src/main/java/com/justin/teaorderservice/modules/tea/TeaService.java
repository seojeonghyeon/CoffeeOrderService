package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.tea.dto.TeaSearchCondition;
import com.justin.teaorderservice.modules.tea.dto.TeaSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeaService{

    private final TeaRepository teaRepository;

    public List<Tea> findAll() {
        return teaRepository.findAll();
    }

    public Tea findById(Long id) {
        return teaRepository.findById(id).orElse(null);
    }

    public Page<TeaSearchDto> search(TeaSearchCondition teaSearchCondition, Pageable pageable){
        return teaRepository.search(teaSearchCondition, pageable);
    }

}
