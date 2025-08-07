/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dhn.enums.SlotStatus;
import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.ParkingSlot;
import com.dhn.repositories.ParkingLotRepository;
import com.dhn.repositories.ParkingSlotRepository;

import com.dhn.services.ParkingLotService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dhngu
 */
@Service
@Transactional
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepo;
    @Autowired
    private ParkingSlotRepository parkingSlotRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<ParkingLot> getParkingLots(Map<String, String> params) {
        return this.parkingLotRepo.getParkingLots(params);
    }

    @Override
    public ParkingLot getParkingLotById(int id) {
        return this.parkingLotRepo.getParkingLotById(id);
    }
    
    @Override
    public ParkingLot getParkingLotWithReviews(int id) {
        return this.parkingLotRepo.getParkingLotWithReviews(id);
    }
            
    @Override
    public void addOrUpdateParkingLot(ParkingLot p) {
        if (!p.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(p.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                p.setImg(res.get("secure_url").toString());
            } catch (IOException ex) {
            }
        } else if (p.getLotId() != null) {
            ParkingLot old = parkingLotRepo.getParkingLotById(p.getLotId());
            p.setImg(old.getImg());
        }

        boolean isNew = (p.getLotId() == null);

        this.parkingLotRepo.addOrUpdateParkingLot(p);

        if (isNew) {
            for (int i = 1; i <= p.getTotalSlots(); i++) {
                ParkingSlot slot = new ParkingSlot();
                slot.setSlotCode("SLOT-" + i);
                slot.setStatus(SlotStatus.AVAILABLE);
                slot.setLotId(p);
                parkingSlotRepo.addParkingSlot(slot);
            }
        } else {
            List<ParkingSlot> existingSlots = parkingSlotRepo.getParkingSlotsByLotId(p.getLotId());
            int currentCount = existingSlots != null ? existingSlots.size() : 0;

            if (p.getTotalSlots() > currentCount) {
                for (int i = currentCount + 1; i <= p.getTotalSlots(); i++) {
                    ParkingSlot slot = new ParkingSlot();
                    slot.setSlotCode("SLOT-" + i);
                    slot.setStatus(SlotStatus.AVAILABLE);
                    slot.setLotId(p);
                    parkingSlotRepo.addParkingSlot(slot);
                }

            } else if (p.getTotalSlots() < currentCount) {
                // Giảm slot - chỉ xoá các slot chưa được đặt (AVAILABLE)
                List<ParkingSlot> allSlots = parkingSlotRepo.getParkingSlotsByLotId(p.getLotId());

                int toRemove = currentCount - p.getTotalSlots();

                for (int i = allSlots.size() - 1; i >= 0 && toRemove > 0; i--) {
                    ParkingSlot slot = allSlots.get(i);

                    if (slot.getStatus() == SlotStatus.AVAILABLE) {
                        parkingSlotRepo.deleteParkingSlot(slot.getSlotId());
                        toRemove--;
                    }
                }
                if (toRemove > 0) {
                    System.out.printf("⚠️ Không thể xóa đủ %d slot vì không đủ slot trống!%n", currentCount - p.getTotalSlots());
                }
            }

        }
    }

    @Override
    public void deleteParkingLot(int id) {
        this.parkingLotRepo.deleteParkingLot(id);
    }

}
