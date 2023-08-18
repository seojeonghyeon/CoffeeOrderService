package com.justin.teaorderservice.modules.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class AuthorityRepository {

    private static final Map<Long, Authority> store = new HashMap<>();
    private static long sequence = 0L;

    public Authority save(Authority authority) {
        authority.setId(++sequence);
        log.info("save: authority={}", authority);
        store.put(authority.getId(), authority);
        return authority;
    }

    public Authority findById(Long id) {
        return store.get(id);
    }

    public List<Authority> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
