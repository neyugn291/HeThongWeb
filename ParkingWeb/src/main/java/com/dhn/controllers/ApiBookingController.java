/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.enums.BookingStatus;
import com.dhn.enums.InvoiceStatus;
import com.dhn.enums.PaymentMethod;
import com.dhn.pojo.Booking;
import com.dhn.pojo.Payment;
import com.dhn.pojo.User;
import com.dhn.repositories.PaymentRepository;
import com.dhn.services.BookingService;
import com.dhn.services.PaymentService;
import com.dhn.services.UserService;
import com.dhn.utils.PdfGenerator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dhngu
 */
@RestController
@RequestMapping("/api")
public class ApiBookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentRepository paymentRepo;

    @GetMapping("/bookings")
    @CrossOrigin
    public ResponseEntity<List<Booking>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.bookingService.getBookings(params), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/secure/booking/add")
    public ResponseEntity<?> createBooking(@RequestBody Booking b, Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        b.setUserId(u);
        bookingService.addBooking(b);
        return ResponseEntity.ok("Đặt chỗ và tạo hóa đơn thành công!");

    }

    @DeleteMapping("/booking/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingLot(@PathVariable(value = "id") int id) {
        this.bookingService.deleteBooking(id);
    }

    @GetMapping("/secure/bookings")
    @CrossOrigin
    public ResponseEntity<List<Booking>> getMyBookings(Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        Map<String, String> params = new HashMap<>();
        params.put("userId", u.getUserId() + "");
        List<Booking> list = this.bookingService.getBookings(params);
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/payment-methods")
    @ResponseBody
    public PaymentMethod[] getPaymentMethods() {
        return PaymentMethod.values();
    }

    @GetMapping("/booking/pdf/{bookingId}")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable("bookingId") Integer bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);

            if (booking == null
                    || booking.getPayment() == null
                    || booking.getPayment().getReceiptUrl() == null) {
                return ResponseEntity.notFound().build();
            }

            if (booking.getInvoiceId() == null
                    || !booking.getInvoiceId().getStatus().equals(InvoiceStatus.PAID)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // Đọc file PDF từ đường dẫn đã lưu trong receiptUrl
            Path path = Paths.get(booking.getPayment().getReceiptUrl());

            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // file không tồn tại
            }

            byte[] pdfBytes = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename("invoice-" + bookingId + ".pdf")
                            .build()
            );

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/pay/{bookingId}")
    public ResponseEntity<?> payInvoice(@PathVariable("bookingId") Integer bookingId) {
        Booking b = this.bookingService.getBookingById(bookingId);

        this.bookingService.payBooking(b);
        return ResponseEntity.ok("Thanh toán thành công");
    }

    @DeleteMapping("/secure/booking/delete/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable(value = "bookingId") int bookingId, Principal user) {
        Booking b = bookingService.getBookingById(bookingId);
        System.out.println(b);
        if (b.getInvoiceId() != null && b.getInvoiceId().getStatus() == InvoiceStatus.PAID) {
            System.out.println("1");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể huỷ đặt chỗ đã thanh toán.");
            
        }
        bookingService.deleteBooking(bookingId);

        return ResponseEntity.noContent().build();
    }
    

}
