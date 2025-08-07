/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.Review;
import com.dhn.repositories.ReviewRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
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
public class ReviewRepositoryImpl implements ReviewRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Review> getReviewsByLotId(int lotId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Review> q = b.createQuery(Review.class);
        Root<Review> root = q.from(Review.class);
        q.select(root);

        q.where(b.equal(root.get("lotId").get("lotId"), lotId));

        Query query = s.createQuery(q);
        return query.getResultList();
    }
    
    @Override
    public void addReview(Review review) {
        review.setCreatedAt(new Date());
        Session session = this.factory.getObject().getCurrentSession();
        session.persist(review);
    }
    
    @Override
    public void deleteReview(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Review r = session.get(Review.class, id);
        if (r != null) {
            session.delete(r);
        }
    }
}
