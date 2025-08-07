/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.dhn.enums.SlotStatus;
import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.ParkingSlot;
import com.dhn.repositories.ParkingLotRepository;
import com.dhn.repositories.ParkingSlotRepository;
import com.dhn.services.ParkingSlotService;
import com.dhn.utils.Constants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dhngu
 */
@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepo;
    @Autowired
    private ParkingLotRepository parkingLotRepo;

    @Override
    public List<ParkingSlot> getParkingSlots() {
        return this.parkingSlotRepo.getParkingSlots();
    }

    @Override
    public List<ParkingSlot> getParkingSlotsByLotId(int lotId) {
        return this.parkingSlotRepo.getParkingSlotsByLotId(lotId);
    }

    @Override
    public void addParkingSlot(ParkingSlot slot) {
        this.parkingSlotRepo.addParkingSlot(slot);
    }

    @Override
    public ParkingSlot getParkingSlotById(int slotId) {
        return this.parkingSlotRepo.getParkingSlotById(slotId);
    }

    @Override
    public void updateParkingSlot(ParkingSlot slot, String condition) {
        ParkingSlot existingSlot = this.parkingSlotRepo.getParkingSlotById(slot.getSlotId());

        SlotStatus currentStatus = existingSlot.getStatus();
        SlotStatus newStatus = slot.getStatus();
//        if (condition == Constants.CONDITIONS.get("StartTime")) {
//            if (currentStatus == SlotStatus.BOOKED && newStatus != SlotStatus.OCCUPIED) {
//                throw new IllegalStateException("Slot đang BOOKED chỉ có thể chuyển sang trạng thái OCCUPIED");
//            }
//        } else if (condition == Constants.CONDITIONS.get("EndTime")) {
//            if (currentStatus == SlotStatus.OCCUPIED && newStatus == SlotStatus.BOOKED) {
//                throw new IllegalStateException("Không thể chuyển từ OCCUPIED về BOOKED!");
//            }
//        } else if (currentStatus == SlotStatus.OCCUPIED) {
//            throw new IllegalStateException("Không thể thay đổi trạng thái khi slot đang OCCUPIED");
//        }

        existingSlot.setStatus(newStatus);
        this.parkingSlotRepo.updateParkingSlot(existingSlot);
    }

    @Override
    public void deleteParkingSlot(int slotId) {
        ParkingSlot slot = parkingSlotRepo.getParkingSlotById(slotId);

        if (slot == null) {
            throw new IllegalArgumentException("Không tìm thấy slot với ID: " + slotId);
        }

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new IllegalStateException("Chỉ có thể xóa slot khi trạng thái là AVAILABLE.");
        }

        ParkingLot lot = slot.getLotId(); // slot.getLotId() trả về đối tượng ParkingLot

        if (lot != null && lot.getTotalSlots() != null && lot.getTotalSlots() > 0) {
            lot.setTotalSlots(lot.getTotalSlots() - 1);
            this.parkingLotRepo.addOrUpdateParkingLot(lot); // gọi repo cập nhật lại lot
        }

        this.parkingSlotRepo.deleteParkingSlot(slotId);
    }

}
