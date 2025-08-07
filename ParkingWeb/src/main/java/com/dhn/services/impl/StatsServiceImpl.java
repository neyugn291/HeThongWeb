/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.dhn.repositories.StatsRepository;
import com.dhn.services.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dhngu
 */
@Service
@Transactional
public class StatsServiceImpl implements StatsService{
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> statsRevenueByParkingLot() {
        return this.statsRepo.statsRevenueByParkingLot();
    }

    @Override
    public List<Object[]> statsRevenueByTime(int day, int month, int year) {
        return this.statsRepo.statsRevenueByTime(day, month, year);
    }

    @Override
    public List<Object[]> statsTopCustomersByRevenue(int topN) {
        return this.statsRepo.statsTopCustomersByRevenue(topN);
    }
   
}
