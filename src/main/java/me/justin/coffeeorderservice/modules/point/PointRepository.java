package me.justin.coffeeorderservice.modules.point;

import me.justin.coffeeorderservice.modules.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByIdAndMember(Long id, Member member);
}
