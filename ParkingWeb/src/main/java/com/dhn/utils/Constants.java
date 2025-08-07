/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.utils;

import com.dhn.pojo.Booking;
import com.dhn.pojo.ParkingSlot;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dhngu
 */
public final class Constants {

    private Constants() {
    } // Không cho tạo object

    public static final Map<String, String> CONDITIONS;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("EndTime", "Het thoi gian su dung");
        map.put("StartTime", "Den thoi gian su dung");

        CONDITIONS = Collections.unmodifiableMap(map);
    }

    public static BigDecimal calculateTotalAmount(Booking booking,BigDecimal price) {
        if (booking.getStartTime() == null || booking.getEndTime() == null || booking.getSlotId() == null)
            return BigDecimal.ZERO;
      
        ParkingSlot slot = booking.getSlotId();

        Date start = booking.getStartTime();
        Date end = booking.getEndTime();

        long millis = end.getTime() - start.getTime();
        long hours = (long) Math.ceil(millis / (1000.0 * 60 * 60));
        
        return price.multiply(BigDecimal.valueOf(hours)).setScale(2, RoundingMode.HALF_UP);
    }
}
