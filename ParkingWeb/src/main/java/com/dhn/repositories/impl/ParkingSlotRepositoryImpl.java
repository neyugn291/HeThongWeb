/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.ParkingSlot;
import com.dhn.repositories.ParkingSlotRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
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
public class ParkingSlotRepositoryImpl implements ParkingSlotRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<ParkingSlot> getParkingSlots() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ParkingSlot> q = b.createQuery(ParkingSlot.class);
        Root root = q.from(ParkingSlot.class);
        q.select(root);

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public List<ParkingSlot> getParkingSlotsByLotId(int lotId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ParkingSlot> q = b.createQuery(ParkingSlot.class);
        Root<ParkingSlot> root = q.from(ParkingSlot.class);
        q.select(root);

        q.where(b.equal(root.get("lotId").get("lotId"), lotId));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public ParkingSlot getParkingSlotById(int slotId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<ParkingSlot> cq = cb.createQuery(ParkingSlot.class);
        Root<ParkingSlot> root = cq.from(ParkingSlot.class);

        root.fetch("lotId", JoinType.INNER);

        cq.select(root).where(cb.equal(root.get("slotId"), slotId));

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public ParkingSlot addParkingSlot(ParkingSlot slot) {
        Session session = this.factory.getObject().getCurrentSession();
        session.persist(slot);
        return slot;
    }

    @Override
    public void updateParkingSlot(ParkingSlot slot) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(slot);
    }

    @Override
    public void deleteParkingSlot(int slotId) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingSlot slot = s.get(ParkingSlot.class, slotId);
        if (slot != null) {
            s.delete(slot);
        }
    }

}
