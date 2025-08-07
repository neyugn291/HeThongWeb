/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.formatters;

import com.dhn.pojo.ParkingSlot;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author dhngu
 */
public class ParkingSlotFormatter implements Formatter<ParkingSlot>{

    @Override
    public String print(ParkingSlot s, Locale locale) {
        return String.valueOf(s.getSlotId());
    }

    @Override
    public ParkingSlot parse(String slotId, Locale locale) throws ParseException {
        ParkingSlot s = new ParkingSlot(Integer.valueOf(slotId));
        return s;
    }
    
}
