/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.dhn.enums.BookingStatus;
import com.dhn.enums.InvoiceStatus;
import com.dhn.enums.SlotStatus;
import com.dhn.pojo.Booking;
import com.dhn.pojo.Invoice;
import com.dhn.pojo.LicensePlate;
import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.ParkingSlot;
import com.dhn.repositories.BookingRepository;
import com.dhn.repositories.InvoiceRepository;
import com.dhn.repositories.LicensePlateRepository;
import com.dhn.repositories.ParkingLotRepository;
import com.dhn.repositories.ParkingSlotRepository;
import com.dhn.repositories.PaymentRepository;
import com.dhn.services.BookingService;
import com.dhn.services.InvoiceService;
import com.dhn.services.NotificationService;
import com.dhn.services.ParkingSlotService;
import com.dhn.utils.Constants;
import com.dhn.utils.PdfGenerator;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dhngu
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private ParkingSlotRepository parkingSlotRepo;

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private ParkingLotRepository parkingLotRepo;
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private LicensePlateRepository plateRepo;

    @Override
    public List<Booking> getBookings(Map<String, String> params) {
        return this.bookingRepo.getBookings(params);
    }

    @Override
    public Booking getBookingById(int id) {
        return this.bookingRepo.getBookingById(id);
    }

    @Override
    public void addBooking(Booking b) {
        ParkingSlot slot = this.parkingSlotRepo.getParkingSlotById(b.getSlotId().getSlotId());
        slot.setStatus(SlotStatus.BOOKED);
        this.parkingSlotRepo.updateParkingSlot(slot);
        
        
        LicensePlate lp = plateRepo.getPlateById(b.getLicensePlateId().getId());
        b.setLicensePlateId(lp);
        
        
        int plateId = b.getLicensePlateId().getId();
        Date start = b.getStartTime();
        Date end = b.getEndTime();

        if (bookingRepo.existsOverlappingBooking(plateId, start, end)) {
            throw new IllegalArgumentException("Biển số xe này đã có lịch đặt trong khoảng thời gian bạn chọn.");
        }

        this.bookingRepo.addBooking(b);

        ParkingLot p = this.parkingLotRepo.getParkingLotById(b.getSlotId().getLotId().getLotId());

        Invoice invoice = new Invoice();
        invoice.setBookingId(b);
        invoice.setUserId(b.getUserId());
        invoice.setInvoiceDate(new Date());
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setTotalAmount(Constants.calculateTotalAmount(b, p.getPricePerHour()));
        b.getPayment().setBookingId(b);
        b.getPayment().setAmount(Constants.calculateTotalAmount(b, p.getPricePerHour()));
        
        b = this.bookingRepo.getBookingById(b.getBookingId());
        this.invoiceRepo.addInvoice(invoice);

        try {
            String folderPath = "src/main/resources/static/invoices";
            String filePath = PdfGenerator.generateInvoiceAndSaveToFile(b, folderPath);

            b.getPayment().setReceiptUrl(filePath);
            this.paymentRepo.updatePayment(b.getPayment());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBooking(int bookingId) {
        Booking b = this.bookingRepo.getBookingById(bookingId);
        ParkingSlot sl = this.parkingSlotRepo.getParkingSlotById(b.getSlotId().getSlotId());
        sl.setStatus(SlotStatus.AVAILABLE);
        this.parkingSlotRepo.updateParkingSlot(sl);
        this.bookingRepo.deleteBooking(bookingId);
    }

    @Override
    public void payBooking(Booking b) {
        b.getPayment().setPaymentTime(new Date());
        this.paymentRepo.updatePayment(b.getPayment());
        b.getInvoiceId().setStatus(InvoiceStatus.PAID);
        this.invoiceRepo.updateInvoice(b.getInvoiceId());
    }

    @Autowired
    private ParkingSlotService parkingSlotService;
    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 60000)
    public void updateSlotStatusByTime() {

        Map<String, String> params = new HashMap<>();
        params.put("status", "ACTIVE");
        String nowFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        params.put("toStart", nowFormatted);

        List<Booking> bookings = bookingRepo.getBookings(params);
        String condition = Constants.CONDITIONS.get("StartTime");

        for (Booking b : bookings) {
            ParkingSlot slot = b.getSlotId();

            if (!b.isSlotStarted() && slot != null && slot.getStatus() != SlotStatus.OCCUPIED) {
                slot.setStatus(SlotStatus.OCCUPIED);
                parkingSlotService.updateParkingSlot(slot, condition);

                b.setSlotStarted(true);
                bookingRepo.updateBooking(b);

                notificationService.notify(
                        b.getUserId(),
                        "Chỗ đậu xe " + slot.getSlotCode() + " của bạn đã được kích hoạt."
                );
                System.out.println("Đã gửi thông báo bắt đầu: bookingId = " + b.getBookingId());
            }
        }

        Map<String, String> paramsEnd = new HashMap<>();
        paramsEnd.put("status", "ACTIVE");
        paramsEnd.put("toEnd", nowFormatted);
        List<Booking> endingBookings = bookingRepo.getBookings(paramsEnd);
        condition = Constants.CONDITIONS.get("EndTime");

        for (Booking b : endingBookings) {
            ParkingSlot slot = b.getSlotId();

            if (!b.isSlotEnded() && slot != null && slot.getStatus() == SlotStatus.OCCUPIED) {
                slot.setStatus(SlotStatus.AVAILABLE);
                parkingSlotService.updateParkingSlot(slot, condition);

                b.setSlotEnded(true);
                b.setStatus(BookingStatus.COMPLETED);
                bookingRepo.updateBooking(b);

                notificationService.notify(
                        b.getUserId(),
                        "Chỗ đậu xe " + slot.getSlotCode() + " của bạn đã kết thúc."
                );
                System.out.println("Đã gửi thông báo kết thúc: bookingId = " + b.getBookingId());
            }
        }

    }

    @Scheduled(fixedRate = 60000)
    public void cancelExpiredUnpaidBookings() {
        String nowFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Map<String, String> params = new HashMap<>();
        params.put("status", "ACTIVE");
        params.put("toStart", nowFormatted);

        List<Booking> bookings = bookingRepo.getBookings(params);

        for (Booking b : bookings) {
            if (b.getInvoiceId() != null && b.getInvoiceId().getStatus() == InvoiceStatus.UNPAID) {
                b.getSlotId().setStatus(SlotStatus.AVAILABLE);
                b.setStatus(BookingStatus.CANCELLED);
                this.bookingRepo.updateBooking(b);

                notificationService.notify(
                        b.getUserId(),
                        "Đặt chỗ của bạn đã bị huỷ do chưa thanh toán trước giờ bắt đầu."
                );
                System.out.println("Đã huỷ booking quá hạn chưa thanh toán: " + b.getBookingId());

            }
        }
    }

    @Override
    public void cancelByAdmin(Booking b) {
        if (b.getStatus() != BookingStatus.ACTIVE) {
            throw new IllegalStateException("Booking không ở trạng thái có thể huỷ.");
        }
        if (b.getInvoiceId() != null) {
            b.getSlotId().setStatus(SlotStatus.AVAILABLE);
            b.setStatus(BookingStatus.CANCELLED);
            this.bookingRepo.updateBooking(b);

            notificationService.notify(
                    b.getUserId(),
                    "Đặt chỗ của bạn đã bị huỷ do admin."
            );
        }
    }

}
