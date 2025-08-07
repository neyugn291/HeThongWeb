/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.repositories;

import com.dhn.pojo.ParkingLot;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhngu
 */
public interface ParkingLotRepository {
    List<ParkingLot> getParkingLots(Map<String, String> params);
    ParkingLot getParkingLotById(int id);
    ParkingLot getParkingLotWithReviews(int id);
    void addOrUpdateParkingLot(ParkingLot p);
    void deleteParkingLot(int id);
}
