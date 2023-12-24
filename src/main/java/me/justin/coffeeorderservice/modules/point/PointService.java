package me.justin.coffeeorderservice.modules.point;

import me.justin.coffeeorderservice.infra.exception.ErrorCode;
import me.justin.coffeeorderservice.infra.exception.NoSuchMemberException;
import me.justin.coffeeorderservice.infra.exception.NoSuchPointException;
import me.justin.coffeeorderservice.modules.event.PointCreatedEvent;
import me.justin.coffeeorderservice.modules.event.PointUpdateEvent;
import me.justin.coffeeorderservice.modules.member.Member;
import me.justin.coffeeorderservice.modules.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

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
        Point savePoint = pointRepository.save(point);
        eventPublisher.publishEvent(new PointCreatedEvent(savePoint));
        return savePoint;
    }

    @Transactional
    public void cancel(Member member, Long pointId){
        Point point = pointRepository.findByIdAndMember(pointId, member).orElseThrow(()-> new NoSuchPointException(ErrorCode.NO_SUCH_POINT));
        point.cancel();
        eventPublisher.publishEvent(new PointUpdateEvent(point));
    }
}
