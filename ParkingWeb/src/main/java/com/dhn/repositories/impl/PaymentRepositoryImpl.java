/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.Payment;
import com.dhn.repositories.PaymentRepository;
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
