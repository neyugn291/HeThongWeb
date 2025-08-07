/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.repositories.impl;

import com.dhn.hibertnateparking.HibernateUtils;
import com.dhn.pojo.ParkingLot;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author dhngu
 */
public class ParkingLotRepositoryImpl {
    private static final int PAGESIZE = 6;
    
    public List<ParkingLot> getParkingLots(Map<String, String> params) {
        try(Session s = HibernateUtils.getFACTORY().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<ParkingLot> q = b.createQuery(ParkingLot.class);
            Root root = q.from(ParkingLot.class);
            q.select(root);
            
            // Loc du lieu
            if(params != null) {
                List<Predicate> predicates = new ArrayList<>();
                
                String kw = params.get("kw");
                
                if(kw != null && !kw.isEmpty()) {
                    predicates.add((Predicate) b.like(root.get("name"),String.format("%%%s%%",kw)));
                }
                
                q.where(predicates.toArray(Predicate[]::new));
            }
            
            
            Query query = s.createQuery(q);
            
            if(params != null) {
                String page = params.get("page");
                if(page != null) {
                    int p =Integer.parseInt(page);
                    int start = (p-1) * PAGESIZE;
                    
                    query.setFirstResult(start);
                    query.setMaxResults(PAGESIZE);
                }
            }
            return query.getResultList();
        }
    }
}
