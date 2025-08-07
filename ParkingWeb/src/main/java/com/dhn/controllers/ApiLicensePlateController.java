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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dhngu
 */
@RestController
@RequestMapping("/api")
public class ApiLicensePlateController {

    @Autowired
    private LicensePlateService licensePlateService;

    @Autowired
    private UserService userService;

    @GetMapping("/secure/plates")
    public ResponseEntity<List<LicensePlate>> getUserPlates(Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        return new ResponseEntity<>(this.licensePlateService.getPlatesByUsername(u.getUsername()), HttpStatus.OK);
    }
}
