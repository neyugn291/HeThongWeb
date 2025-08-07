/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.formatters;

import com.dhn.pojo.LicensePlate;
import com.dhn.services.LicensePlateService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author dhngu
 */
@Component
public class LicensePlateFormatter implements Formatter<LicensePlate> {
    @Autowired
    private LicensePlateService licensePlateService;

    @Override
    public LicensePlate parse(String id, Locale locale) throws ParseException {
        return licensePlateService.getPlateById(Integer.parseInt(id));
    }

    @Override
    public String print(LicensePlate licensePlate, Locale locale) {
        return licensePlate.getId().toString();
    }
}

