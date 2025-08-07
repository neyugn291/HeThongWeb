/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.dhn.pojo.LicensePlate;
import com.dhn.repositories.LicensePlateRepository;
import com.dhn.services.LicensePlateService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dhngu
 */
@Service
@Transactional
public class LicensePlateServiceImpl implements LicensePlateService{
    @Autowired
    private LicensePlateRepository plateRepo;
    

    @Override
    public LicensePlate getPlateById(int id) {
        return this.plateRepo.getPlateById(id);
    }

    @Override
    public List<LicensePlate> getPlatesByUsername(String username) {
        return this.plateRepo.getPlatesByUsername(username);
    }

    @Override
    public void addPlate(LicensePlate licensePlate) {
        licensePlate.setCreatedAt(new Date());
        this.plateRepo.addPlate(licensePlate);
    }

    @Override
    public List<LicensePlate> getPlates() {
        return this.plateRepo.getPlates();
    }

    @Override
    public void updatePlate(LicensePlate licensePlate) {
        this.plateRepo.updatePlate(licensePlate);
    }

    @Override
    public void deletePlate(int id) {
        this.plateRepo.deletePlate(id);
    }
    
}
