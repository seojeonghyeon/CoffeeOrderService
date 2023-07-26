package com.justin.teaorderservice;

import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.TeaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final TeaRepository teaRepository;

    @PostConstruct
    public void init(){
        teaRepository.save(new Tea("Americano(Hot)", 2000, 10000,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 뜨거운 물을 희석시켜 만든 음료"));
        teaRepository.save(new Tea("Americano(Ice)", 2000, 10,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 찬 물을 희석시켜 만든 음료"));
        teaRepository.save(new Tea("Caffe Latte(Hot)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료"));
        teaRepository.save(new Tea("Caffe Latte(Ice)", 2500, 20,
                "https://cdn.paris.spl.li/wp-content/uploads/%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B3%E1%84%85%E1%85%A1%E1%84%84%E1%85%A6-1280x1280.jpg",
                "리스트레또 더블샷과 스팀우유를 넣어 만든 베이직 커피 음료"));
    }
}
