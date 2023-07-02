package com.justin.teaorderservice;

import com.justin.teaorderservice.order.Tea;
import com.justin.teaorderservice.order.TeaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final TeaRepository teaRepository;

    @PostConstruct
    public void init(){
        teaRepository.save(new Tea("Americano(Hot)", 2000, 10000));
        teaRepository.save(new Tea("Americano(Ice)", 2000, 10000));
    }
}
