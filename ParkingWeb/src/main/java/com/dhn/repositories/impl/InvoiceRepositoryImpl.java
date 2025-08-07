/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.Invoice;
import com.dhn.repositories.InvoiceRepository;
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
public class InvoiceRepositoryImpl implements InvoiceRepository{

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Invoice addInvoice(Invoice invoice) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(invoice);
        return invoice;
    }
    
    @Override
    public void updateInvoice(Invoice i) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(i);
    }
    
    @Override
    public void deleteInvoice(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Invoice i = session.get(Invoice.class, id);
        if (i != null) {
            session.delete(i);
        }
    }
}
