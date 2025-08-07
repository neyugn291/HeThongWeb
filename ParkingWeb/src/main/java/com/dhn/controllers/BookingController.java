/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.enums.BookingStatus;
import com.dhn.enums.PaymentMethod;
import com.dhn.pojo.Booking;
import com.dhn.pojo.LicensePlate;
import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.ParkingSlot;
import com.dhn.pojo.Payment;
import com.dhn.pojo.User;
import com.dhn.repositories.BookingRepository;
import com.dhn.services.BookingService;
import com.dhn.services.LicensePlateService;
import com.dhn.services.ParkingLotService;
import com.dhn.services.ParkingSlotService;
import com.dhn.services.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dhngu
 */
@Controller
public class BookingController {

    @Autowired
    private ParkingSlotService parkingSlotService;
    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private LicensePlateService plateService;

    @GetMapping("/bookings")
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getBookings(null);
        model.addAttribute("bookings", bookings);
        List<BookingStatus> statuses = List.of(BookingStatus.values());
        model.addAttribute("statuses", statuses);
        return "bookings";
    }

    @GetMapping("/booking/add")
    public String showBookingForm(@RequestParam("lotId") int lotId, Model model, Principal principal) {
        List<ParkingSlot> slots = this.parkingSlotService.getParkingSlotsByLotId(lotId);
        ParkingLot p = this.parkingLotService.getParkingLotById(lotId);
        User u = this.userService.getUserByUsername(principal.getName());
        Booking b = new Booking();
        b.setPayment(new Payment());
        Set<LicensePlate> plates = u.getLicensePlates();
        model.addAttribute("plates", plates);
        model.addAttribute("slots", slots);
        model.addAttribute("name", p.getName());
        model.addAttribute("booking", b);

        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "add-booking";
    }

    @PostMapping("/booking/add")
    public String addBooking(@ModelAttribute("booking") Booking booking, Principal principal, Model model) {
        User u = this.userService.getUserByUsername(principal.getName());
        booking.setUserId(u);
        ParkingSlot selectedSlot = this.parkingSlotService.getParkingSlotById(booking.getSlotId().getSlotId());

        if (!"Available".equalsIgnoreCase(selectedSlot.getStatus().toString())) {
            model.addAttribute("error", "Chỗ đậu đã được đặt hoặc không khả dụng!");
            model.addAttribute("slots", this.parkingSlotService.getParkingSlotsByLotId(selectedSlot.getLotId().getLotId()));
            model.addAttribute("name", selectedSlot.getLotId().getName());
            model.addAttribute("booking", booking);
            return "add-booking";
        }
        booking.setSlotId(selectedSlot);
        System.out.println(booking.getPayment().getPaymentMethod());

        this.bookingService.addBooking(booking);
        return "redirect:/";
    }
    @Autowired
    private BookingRepository bookingRepo;

    @PostMapping("/booking/update/{id}")
    public String updateBookingStatus(@PathVariable("id") int id,
            @RequestParam("status") BookingStatus status) {
        Booking b = bookingService.getBookingById(id);
        b.setStatus(status);
        this.bookingRepo.updateBooking(b);
        return "redirect:/bookings";
    }

}
