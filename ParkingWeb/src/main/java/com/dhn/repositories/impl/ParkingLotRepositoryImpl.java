/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.ParkingLot;
import com.dhn.repositories.ParkingLotRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * //
 *
 *
 * @author dhngu
 */
@Repository
@Transactional
public class ParkingLotRepositoryImpl implements ParkingLotRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGESIZE = 6;

    @Override
    public List<ParkingLot> getParkingLots(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ParkingLot> q = b.createQuery(ParkingLot.class);
        Root root = q.from(ParkingLot.class);
        q.select(root);

    
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add((Predicate) b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            String address = params.get("address");
            if (address != null && !address.isEmpty()) {
                predicates.add(b.like(root.get("address"), String.format("%%%s%%", address)));
            }

            String price = params.get("fromPrice");
            if (price != null && !price.isEmpty()) {
                try {
                    double p = Double.parseDouble(price);
                    predicates.add(b.greaterThanOrEqualTo(root.get("pricePerHour"), p));
                } catch (NumberFormatException ex) {
                }
            }

            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                try {
                    double tp = Double.parseDouble(toPrice);
                    predicates.add(b.lessThanOrEqualTo(root.get("pricePerHour"), tp));
                } catch (NumberFormatException ex) {
                }
            }

            String totalSlots = params.get("totalSlots");
            if (totalSlots != null && !totalSlots.isEmpty()) {
                try {
                    int slots = Integer.parseInt(totalSlots);
                    predicates.add(b.greaterThanOrEqualTo(root.get("totalSlots"), slots));
                } catch (NumberFormatException ex) {
                }
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int p = Integer.parseInt(page);
                int start = (p - 1) * PAGESIZE;

                query.setFirstResult(start);
                query.setMaxResults(PAGESIZE);
            }
        }
        return query.getResultList();
    }

    @Override
    public ParkingLot getParkingLotById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<ParkingLot> cq = cb.createQuery(ParkingLot.class);
        Root<ParkingLot> root = cq.from(ParkingLot.class);

        cq.select(root).where(cb.equal(root.get("lotId"), id));

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public ParkingLot getParkingLotWithReviews(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingLot lot = s.get(ParkingLot.class, id);
        Hibernate.initialize(lot.getReviewSet());
        return lot;
    }

    @Override
    public void addOrUpdateParkingLot(ParkingLot p) {
        Session s = this.factory.getObject().getCurrentSession();
        if (p.getLotId() == null) {
            s.persist(p);
        } else {
            s.merge(p);
        }
    }

    @Override
    public void deleteParkingLot(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        ParkingLot p = session.get(ParkingLot.class, id);
        if (p != null) {
            session.delete(p);
        }
    }

}
