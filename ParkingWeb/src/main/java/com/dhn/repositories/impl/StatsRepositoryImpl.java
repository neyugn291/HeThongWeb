/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.enums.InvoiceStatus;
import com.dhn.pojo.Booking;
import com.dhn.pojo.Invoice;
import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.ParkingSlot;
import com.dhn.pojo.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import com.dhn.repositories.StatsRepository;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dhngu
 */
@Repository
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsRevenueByParkingLot() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);
        Root root = query.from(Invoice.class);

        Join<Invoice, Booking> bookingJoin = root.join("bookingId");
        Join<Booking, ParkingSlot> slotJoin = bookingJoin.join("slotId");
        Join<ParkingSlot, ParkingLot> lotJoin = slotJoin.join("lotId");

        Predicate isPaid = b.equal(root.get("status"), InvoiceStatus.PAID);

        query.where(isPaid);
        query.groupBy(lotJoin.get("name"));

        query.multiselect(
                lotJoin.get("name"),
                b.sum(root.get("totalAmount")),
                b.count(root)
        );

        return session.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByTime(int day, int month, int year) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);
        Root<Invoice> root = query.from(Invoice.class);

        Predicate isPaid = b.equal(root.get("status"), InvoiceStatus.PAID);

        Predicate byDay = b.equal(b.function("DAY", Integer.class, root.get("invoiceDate")), day);
        Predicate byMonth = b.equal(b.function("MONTH", Integer.class, root.get("invoiceDate")), month);
        Predicate byYear = b.equal(b.function("YEAR", Integer.class, root.get("invoiceDate")), year);

        query.where(b.and(isPaid, byDay, byMonth, byYear));

        query.multiselect(
                b.sum(root.get("totalAmount")),
                b.count(root)
        );

        return session.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> statsTopCustomersByRevenue(int topN) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);
        Root<Invoice> root = query.from(Invoice.class);

        Join<Invoice, User> userJoin = root.join("userId");

        Predicate isPaid = b.equal(root.get("status"), InvoiceStatus.PAID);
        query.where(isPaid);

        query.groupBy(userJoin.get("id"), userJoin.get("fullName"));

        query.multiselect(
                userJoin.get("fullName"),
                b.sum(root.get("totalAmount"))
        );

        query.orderBy(b.desc(b.sum(root.get("totalAmount"))));

        Query<Object[]> q = session.createQuery(query);
        q.setMaxResults(topN);

        return q.getResultList();
    }

}
