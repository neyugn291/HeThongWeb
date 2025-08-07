/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.repositories;

import java.util.List;

/**
 *
 * @author dhngu
 */

public interface StatsRepository {
    public List<Object[]> statsRevenueByParkingLot();
    
    public List<Object[]> statsRevenueByTime(int day, int month, int year);
    
    public List<Object[]> statsTopCustomersByRevenue(int topN);
}
