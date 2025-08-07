/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.repositories;

import com.dhn.pojo.LicensePlate;
import java.util.List;

/**
 *
 * @author dhngu
 */
public interface LicensePlateRepository {

    public List<LicensePlate> getPlates();
    
    public LicensePlate getPlateById(int id);

    public List<LicensePlate> getPlatesByUsername(String username);

    public void addPlate(LicensePlate licensePlate);
    
    public void updatePlate(LicensePlate licensePlate);
    
    public void deletePlate(int id);

}
