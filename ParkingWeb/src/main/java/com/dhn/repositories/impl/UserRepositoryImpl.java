/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.User;
import com.dhn.repositories.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dhngu
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public List<User> getUsers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> u = b.createQuery(User.class);
        Root root = u.from(User.class);
        u.select(root);

        // Loc du lieu
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add((Predicate) b.like(root.get("name"), String.format("%%%s%%", kw)));
            }


            u.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(u);
        return query.getResultList();
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<User> query = session.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);

        List<User> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Override
    @Transactional
    public User addUser(User u) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(u);
        return u;
    }

    @Override
    @Transactional
    public boolean authenticate(String username, String password) {
        System.out.println(">>> Found user? " + username);

        User u = this.getUserByUsername(username);
        System.out.println(">>> Found user? " + username);
        return this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        User u = session.get(User.class, id);
        if (u != null) {
            session.delete(u);
        }
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public User updateUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(u);
        return u;
    }
}
