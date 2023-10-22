package com.justin.teaorderservice.modules.point;

import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.NoSuchMemberException;
import com.justin.teaorderservice.infra.exception.NoSuchPointException;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class PointService {

    PointRepository pointRepository;
    MemberRepository memberRepository;

    public Integer findPointById(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchMemberException::new);
        return member.getPoint();
    }

    public Point findPointByMemberAndPointId(String memberId, Long pointId){
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchMemberException::new);
        return pointRepository.findByIdAndMember(pointId, member).orElseThrow(NoSuchPointException::new);
    }

    @Transactional
    public Point addPoint(String memberId, Integer currentPoint, Integer addPoint){
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchMemberException::new);
        Point point = Point.creatPoint(member, currentPoint, addPoint);
        return pointRepository.save(point);
    }

    @Transactional
    public void cancel(Member member, Long pointId){
        Point point = pointRepository.findByIdAndMember(pointId, member).orElseThrow(()-> new NoSuchPointException(ErrorCode.NO_SUCH_POINT));
        point.cancel();
    }

}
