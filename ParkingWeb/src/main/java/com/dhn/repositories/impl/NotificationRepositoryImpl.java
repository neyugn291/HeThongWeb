/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.Notification;
import com.dhn.repositories.NotificationRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dhngu
 */
@Repository
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Notification> getNotification() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Notification> q = b.createQuery(Notification.class);
        Root root = q.from(Notification.class);
        q.select(root);

        Query query = s.createQuery(q);

        return query.getResultList();
    }
    
    @Override
    public List<Notification> getNotisByUsername(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<Notification> query = session.createNamedQuery("Notification.findByUsername", Notification.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

  
    @Override
    public void addNotification(Notification n) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(n);
    }
    
    @Override
    public void deleteNotification(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Notification n = s.get(Notification.class, id);
        if (n != null) {
            s.delete(n);
        }
    }

}
