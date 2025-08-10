/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.enums.BookingStatus;
import com.dhn.pojo.Booking;
import com.dhn.repositories.BookingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class BookingRepositoryImpl implements BookingRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Booking> getBookings(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Booking> q = b.createQuery(Booking.class);
        Root<Booking> root = q.from(Booking.class);
        root.fetch("userId", JoinType.INNER);

        root.fetch("licensePlateId", JoinType.LEFT);
        root.fetch("slotId", JoinType.INNER);
        q.select(root).distinct(true);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String userId = params.get("userId");
            if (userId != null && !userId.isEmpty()) {
                try {
                    int uid = Integer.parseInt(userId);
                    predicates.add(b.equal(root.get("userId").get("userId"), uid));
                } catch (NumberFormatException ex) {
                }
            }

            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                predicates.add(b.equal(root.get("status"), status));
            }

            String slotId = params.get("slotId");
            if (slotId != null && !slotId.isEmpty()) {
                try {
                    int sid = Integer.parseInt(slotId);
                    predicates.add(b.equal(root.get("slotId").get("slotId"), sid));
                } catch (NumberFormatException ex) {
                }
            }

            String fromDate = params.get("fromDate");
            if (fromDate != null && !fromDate.isEmpty()) {
                try {
                    Date f = java.sql.Timestamp.valueOf(fromDate);
                    predicates.add(b.greaterThanOrEqualTo(root.get("createdAt"), f));
                } catch (IllegalArgumentException ex) {
                }
            }

            String toDate = params.get("toDate");
            if (toDate != null && !toDate.isEmpty()) {
                try {
                    Date t = java.sql.Timestamp.valueOf(toDate);
                    predicates.add(b.lessThanOrEqualTo(root.get("createdAt"), t));
                } catch (IllegalArgumentException ex) {
                }
            }

            String fromStart = params.get("fromStart");
            if (fromStart != null && !fromStart.isEmpty()) {
                try {
                    Date st = java.sql.Timestamp.valueOf(fromStart);
                    predicates.add(b.greaterThanOrEqualTo(root.get("startTime"), st));
                } catch (IllegalArgumentException ex) {
                }
            }

            String toEnd = params.get("toEnd");
            if (toEnd != null && !toEnd.isEmpty()) {
                try {
                    Date et = java.sql.Timestamp.valueOf(toEnd);
                    predicates.add(b.lessThanOrEqualTo(root.get("endTime"), et));
                } catch (IllegalArgumentException ex) {
                }
            }

            String toStart = params.get("toStart");
            if (toStart != null && !toStart.isEmpty()) {
                try {
                    Date ts = java.sql.Timestamp.valueOf(toStart);
                    predicates.add(b.lessThanOrEqualTo(root.get("startTime"), ts));
                } catch (IllegalArgumentException ex) {

                }
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Booking getBookingById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
        Root<Booking> root = cq.from(Booking.class);

        cq.select(root).where(cb.equal(root.get("bookingId"), id));

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public void addBooking(Booking b) {

        Session session = this.factory.getObject().getCurrentSession();
        b.setCreatedAt(new Date());
        b.setStatus(BookingStatus.ACTIVE);
        try {
            System.out.println(new ObjectMapper().writeValueAsString(b));
        } catch (JsonProcessingException ex) {
            System.getLogger(BookingRepositoryImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        session.save(b);
    }

    @Override
    public void updateBooking(Booking b) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(b);
    }

    @Override
    public void deleteBooking(int bookingId) {
        Session session = this.factory.getObject().getCurrentSession();
        Booking b = session.get(Booking.class, bookingId);
        if (b != null) {
            session.delete(b);
        }
    }

    @Override
    public boolean existsOverlappingBooking(int licensePlateId, Date startTime, Date endTime) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Booking> root = cq.from(Booking.class);

        Predicate plateMatch = cb.equal(root.get("licensePlateId").get("id"), licensePlateId);

        Predicate timeOverlap = cb.and(
                cb.lessThan(root.get("startTime"), endTime),
                cb.greaterThan(root.get("endTime"), startTime)
        );

        Predicate statusNotCancelled = cb.notEqual(root.get("status"), BookingStatus.CANCELLED);

        cq.select(cb.count(root)).where(cb.and(plateMatch, timeOverlap, statusNotCancelled));

        Long count = s.createQuery(cq).getSingleResult();

        return count > 0;
    }

}
