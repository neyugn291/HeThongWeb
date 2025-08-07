/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.services;

import com.dhn.pojo.ParkingSlot;
import java.util.List;

/**
 *
 * @author dhngu
 */
public interface ParkingSlotService {
    List<ParkingSlot> getParkingSlots();
    List<ParkingSlot> getParkingSlotsByLotId(int lotId);
    ParkingSlot getParkingSlotById(int slotId);
    void addParkingSlot(ParkingSlot slot);
    void updateParkingSlot(ParkingSlot slot, String condition);
    void deleteParkingSlot(int slotId);
}
