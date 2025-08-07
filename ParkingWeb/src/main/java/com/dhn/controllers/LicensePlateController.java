/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.pojo.LicensePlate;
import com.dhn.pojo.User;
import com.dhn.services.LicensePlateService;
import com.dhn.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dhngu
 */
@Controller
public class LicensePlateController {

    @Autowired
    private LicensePlateService licensePlateService;
    @Autowired
    private UserService userService;

    @GetMapping("/licenseplates")
    public String listPlates(Model model) {
        model.addAttribute("plates", licensePlateService.getPlates());
        model.addAttribute("plate", new LicensePlate());

        return "plates";
    }

    @PostMapping("/licenseplate/add")
    public String add(@ModelAttribute("plate") LicensePlate plate, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        plate.setUser(user);
        licensePlateService.addPlate(plate);

        return "redirect:/licenseplates";
    }

    @PostMapping("/licenseplate/update/{id}")
    public String updateLicensePlate(@PathVariable(value = "id") int id,
            @RequestParam("licensePlate") String licensePlate) {
        LicensePlate plate = licensePlateService.getPlateById(id);
        if (plate != null) {
            plate.setLicensePlate(licensePlate);
            this.licensePlateService.updatePlate(plate);
        }
        return "redirect:/licenseplates";
    }
}
