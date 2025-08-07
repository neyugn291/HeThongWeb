/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.hibertnateparking.HibernateUtils;
import com.dhn.pojo.User;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author dhngu
 */
public class UserRepositoryImpl {
    public List<User> getUsers(Map<String, String> params) {
    try (Session s = HibernateUtils.getFACTORY().openSession()) {
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root root = q.from(User.class);
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate p = b.like(root.get("userName"), "%" + kw + "%");
                predicates.add(p);
            }

            String role = params.get("role");
            if (role != null && !role.isEmpty())
                predicates.add(b.equal(root.get("role"), role));

            q.where(predicates.toArray(new Predicate[0]));

            // Sắp xếp theo ngày tạo mới nhất
            q.orderBy(b.desc(root.get("createdAt")));
        }

        Query query = s.createQuery(q);

        // Pagination
    

        return query.getResultList();
    }
}
    
    public User getUserByUsername (String username) {
        try(Session s = HibernateUtils.getFACTORY().openSession()) {
            Query q = s.createQuery("User.findByUsername", User.class);
            q.setParameter("userName", username);
            
            return (User) q.getSingleResult();
        }
    }
}
