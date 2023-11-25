package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.tea.dto.TeaSearchCondition;
import com.justin.teaorderservice.modules.tea.dto.TeaSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeaRepositoryCustom {
    Page<TeaSearchDto> search(TeaSearchCondition teaSearchCondition, Pageable pageable);
}
