/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.pojo.ParkingSlot;
import com.dhn.services.ParkingSlotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dhngu
 */
@RestController
@RequestMapping("/api")
public class ApiParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @DeleteMapping("/slot/delete/{slotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingLot(@PathVariable(value = "slotId") int slotId) {
        this.parkingSlotService.deleteParkingSlot(slotId);
    }

    @GetMapping("/slots")
    public ResponseEntity<List<ParkingSlot>> list(@RequestParam(required = false,value="lotId") Integer lotId) {
        System.out.println("Received lotId: " + lotId);
        if (lotId != null) {
            return new ResponseEntity<>(this.parkingSlotService.getParkingSlotsByLotId(lotId), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.parkingSlotService.getParkingSlots(), HttpStatus.OK);
    }

    @GetMapping("/slot/{id}")
    public ResponseEntity<ParkingSlot> retrive(@PathVariable int id) {
        return new ResponseEntity<>(this.parkingSlotService.getParkingSlotById(id), HttpStatus.OK);
    }

    @PostMapping("/slot/add")
    public ResponseEntity<?> createSlot(@RequestBody ParkingSlot slot) {
        try {
            parkingSlotService.addParkingSlot(slot);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo slot thành công!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi tạo slot: " + ex.getMessage());
        }
    }

    @PutMapping("/slot/{id}")
    public ResponseEntity<?> updateSlot(@PathVariable int id, @RequestBody ParkingSlot slot) {
        if (slot.getSlotId() != id) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ID trong URL và body không khớp!");
        }

        ParkingSlot existing = parkingSlotService.getParkingSlotById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        parkingSlotService.updateParkingSlot(slot, "");
        return ResponseEntity.ok("Cập nhật thành công!");
    }

}
