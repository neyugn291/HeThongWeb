/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.formatters;

import com.dhn.pojo.ParkingLot;
import com.dhn.services.ParkingLotService;
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
public class ParkingLotFormatter implements Formatter<ParkingLot> {

    @Autowired
    private ParkingLotService parkingLotService;

    @Override
    public ParkingLot parse(String lotId, Locale locale) throws ParseException {
        ParkingLot p = new ParkingLot(Integer.valueOf(lotId));
        return p;
    }

    @Override
    public String print(ParkingLot p, Locale locale) {
        return String.valueOf(p.getLotId());
    }
}
