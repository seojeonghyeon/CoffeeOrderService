package com.justin.teaorderservice.modules.tea;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TeaRepository {
    private final EntityManager em;

    public void save(Tea tea){
        if(tea.getId() == null){
            em.persist(tea);
        }else{
            em.merge(tea);
        }
    }

    public Tea findOne(Long id){
        return em.find(Tea.class, id);
    }

    public List<Tea> findAll(){
        return em.createQuery("select t from Tea t", Tea.class)
                .getResultList();
    }
}
