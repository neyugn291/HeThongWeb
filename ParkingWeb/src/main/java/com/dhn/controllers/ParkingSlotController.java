/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.pojo.ParkingSlot;
import com.dhn.services.ParkingSlotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dhngu
 */
@Controller
@RequestMapping("/parkinglot/{lotId}")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;
    
//    @ModelAttribute
//    public void commonResponses(Model model) {
//        model.addAttribute("parkingLots", this.parkingSlotService.getParkingSlotsByLotId(null));
//    }

    @GetMapping("/slots")
    public String listSlots(@PathVariable(value= "lotId") int lotId, Model model) {
        List<ParkingSlot> slots = parkingSlotService.getParkingSlotsByLotId(lotId);
        model.addAttribute("slots", slots);
        model.addAttribute("lotId", lotId);
        return "parkingslots";
    }
    
    @PostMapping("/slot/update")
    public String updateSlot(@ModelAttribute ParkingSlot slot) {
        parkingSlotService.updateParkingSlot(slot,"");
        return "redirect:/parkinglot/{lotId}/slots";
    }
}
