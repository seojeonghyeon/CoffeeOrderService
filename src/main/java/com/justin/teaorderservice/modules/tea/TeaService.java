package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.ordercount.OrderCountDto;
import com.justin.teaorderservice.modules.ordercount.OrderCountRepository;
import com.justin.teaorderservice.modules.tea.dto.PopularTea;
import com.justin.teaorderservice.modules.tea.dto.TeaSearchCondition;
import com.justin.teaorderservice.modules.tea.dto.TeaSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeaService{
    private static final Integer POPULAR_TEA_NUMBER = 3;
    private static final Integer POPULAR_TEA_DURING_DAYS = 7;

    private final TeaRepository teaRepository;
    private final OrderCountRepository orderCountRepository;

    public List<Tea> findAll() {
        return teaRepository.findAll();
    }

    public Tea findById(Long id) {
        return teaRepository.findById(id).orElse(null);
    }

    public Page<TeaSearchDto> search(TeaSearchCondition teaSearchCondition, Pageable pageable){
        return teaRepository.search(teaSearchCondition, pageable);
    }

    public List<PopularTea> findPopularTeas(){
        List<OrderCountDto> orderCountDtos = orderCountRepository.countOfOrdersPerPeriod(POPULAR_TEA_NUMBER, LocalDate.now().minusDays(POPULAR_TEA_DURING_DAYS), LocalDate.now());
        List<PopularTea> popularTeas = new ArrayList<>();
        orderCountDtos.forEach(orderCountDto -> {
            PopularTea popularTea = teaRepository.popularTea(orderCountDto.getTeaId());
            popularTea.setCount(orderCountDto.getCount());
            popularTeas.add(popularTea);
        });
        return popularTeas;
    }

}
