/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.Booking;
import com.dhn.pojo.Payment;
import com.dhn.repositories.PaymentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
public class PaymentRepositoryImpl implements PaymentRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public Payment getPaymentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
        Root<Payment> root = cq.from(Payment.class);

        cq.select(root).where(cb.equal(root.get("paymentId"), id));

        return s.createQuery(cq).uniqueResult();
    }
    
    @Override
    public void addPayment(Payment p) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(p);
    }
    
    @Override
    public void updatePayment(Payment p) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(p); 
    }
}
