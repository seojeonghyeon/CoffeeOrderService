package com.justin.teaorderservice.modules.point;

import com.justin.teaorderservice.modules.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{

    private final MemberService memberService;

    @Override
    public Integer findPointById(String userId) {
        return memberService.findByUserId(userId).getPoint();
    }
}
