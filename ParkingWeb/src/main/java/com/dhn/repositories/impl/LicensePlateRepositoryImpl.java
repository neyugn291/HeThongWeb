/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.pojo.LicensePlate;
import com.dhn.repositories.LicensePlateRepository;
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
public class LicensePlateRepositoryImpl implements LicensePlateRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<LicensePlate> getPlates() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<LicensePlate> q = b.createQuery(LicensePlate.class);
        Root root = q.from(LicensePlate.class);
        q.select(root);

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public LicensePlate getPlateById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<LicensePlate> cq = cb.createQuery(LicensePlate.class);
        Root<LicensePlate> root = cq.from(LicensePlate.class);

        cq.select(root).where(cb.equal(root.get("id"), id));

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public List<LicensePlate> getPlatesByUsername(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<LicensePlate> query = session.createNamedQuery("LicensePlate.findByUsername", LicensePlate.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public void addPlate(LicensePlate licensePlate) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(licensePlate);
    }

    @Override
    public void updatePlate(LicensePlate licensePlate) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(licensePlate);
    }

    @Override
    public void deletePlate(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        LicensePlate l = s.get(LicensePlate.class, id);
        if (l != null) {
            s.delete(l);
        }
    }

}
