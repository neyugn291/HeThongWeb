/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.pojo.Booking;
import com.dhn.pojo.Review;
import com.dhn.services.ParkingLotService;
import jakarta.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dhngu
 */
@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private ParkingLotService parkingLotService;

    @ModelAttribute
    public void commonResponses(Model model) {

        model.addAttribute("parkingLots", this.parkingLotService.getParkingLots(null));
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("parkingLots", this.parkingLotService.getParkingLots(params));
        model.addAttribute("review", new Review());

        return "index";
    }

}
