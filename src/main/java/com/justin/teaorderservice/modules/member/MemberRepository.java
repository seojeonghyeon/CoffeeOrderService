package com.justin.teaorderservice.modules.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {
    private static final Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByUserId(String userId) {
        return findAll().stream()
                .filter(m -> m.getUserId().equals(userId))
                .findFirst();
    }

    public Optional<Member> findByPhoneNumber(String phoneNumber){
        return findAll().stream()
                .filter(m -> m.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
