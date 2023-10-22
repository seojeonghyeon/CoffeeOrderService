package com.justin.teaorderservice.modules.point;

import com.justin.teaorderservice.infra.exception.AlreadyCompletedPointException;
import com.justin.teaorderservice.infra.exception.AlreadyNotPendingPointException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.TooMuchAddPointException;
import com.justin.teaorderservice.modules.common.BaseEntity;
import com.justin.teaorderservice.modules.member.Member;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "points") @Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Point extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private PointStatus status;

    private Integer currentPoint;

    private Integer addPoint;

    public static Point creatPoint(Member member, Integer currentPoint, Integer addPoint){
        Point point = Point.builder()
                .member(member)
                .status(PointStatus.PENDING)
                .currentPoint(currentPoint)
                .addPoint(addPoint)
                .build();
        point.inducePoint();
        return point;
    }

    public void inducePoint(){
        if(status != PointStatus.PENDING){
            throw new AlreadyNotPendingPointException(ErrorCode.ALREADY_NOT_PENDING_POINT);
        }
        status = 0 < addPoint && addPoint <= 200_000 ? PointStatus.CONFIRMED : PointStatus.REJECTED;
        if(status == PointStatus.CONFIRMED){
            member.inducePoint(addPoint);
        } else if(status == PointStatus.REJECTED){
            throw new TooMuchAddPointException(ErrorCode.TOO_MUCH_ADD_POINT);
        }
    }

    public void cancel(){
        if(status == PointStatus.COMPLETED){
            throw new AlreadyCompletedPointException(ErrorCode.ALREADY_COMPLETED_POINT);
        }
        status = PointStatus.CANCELED;
        member.deductPoint(addPoint);
    }


}
