/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.services;

import com.dhn.pojo.Booking;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhngu
 */
public interface BookingService {
    List<Booking> getBookings(Map<String, String> params);
    Booking getBookingById(int id);
    void addBooking(Booking b);
    void deleteBooking(int bookingId);
    public void payBooking(Booking b);
    public void cancelByAdmin(Booking b);
}
