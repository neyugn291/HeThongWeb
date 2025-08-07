/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.pojo;

import com.dhn.enums.BookingStatus;
import com.dhn.enums.BookingStatus.BookingStatusConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author dhngu
 */
@Entity
@Table(name = "booking")
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
    @NamedQuery(name = "Booking.findByBookingId", query = "SELECT b FROM Booking b WHERE b.bookingId = :bookingId"),
    @NamedQuery(name = "Booking.findByStartTime", query = "SELECT b FROM Booking b WHERE b.startTime = :startTime"),
    @NamedQuery(name = "Booking.findByEndTime", query = "SELECT b FROM Booking b WHERE b.endTime = :endTime"),
    @NamedQuery(name = "Booking.findByStatus", query = "SELECT b FROM Booking b WHERE b.status = :status"),
    @NamedQuery(name = "Booking.findByCreatedAt", query = "SELECT b FROM Booking b WHERE b.createdAt = :createdAt"),
    @NamedQuery(
            name = "Booking.findAllWithDetails",
            query = "SELECT b FROM Booking b "
            + "JOIN FETCH b.userId "
            + "JOIN FETCH b.licensePlateId "
            + "JOIN FETCH b.slotId"
    )})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "booking_id")
    private Integer bookingId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "license_plate_id")
    private LicensePlate licensePlateId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "status")
    @Convert(converter = BookingStatusConverter.class)
    private BookingStatus status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "slot_id", referencedColumnName = "slot_id")
    @ManyToOne
    private ParkingSlot slotId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    @JsonIgnore
    private User userId;
    @OneToOne(mappedBy = "bookingId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Payment payment;
    @OneToOne(mappedBy = "bookingId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("bookingId")
    private Invoice invoiceId;
    @Column(name = "slot_started", nullable = false)
    private boolean slotStarted = false;
    @Column(name = "slot_ended", nullable = false)
    private boolean slotEnded = false;

    public Booking() {
    }

    public Booking(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public LicensePlate getLicensePlateId() {
        return licensePlateId;
    }

    public void setLicensePlateId(LicensePlate licensePlateId) {
        this.licensePlateId = licensePlateId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ParkingSlot getSlotId() {
        return slotId;
    }

    public void setSlotId(ParkingSlot slotId) {
        this.slotId = slotId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    public boolean isSlotStarted() {
        return slotStarted;
    }

    public void setSlotStarted(boolean slotStarted) {
        this.slotStarted = slotStarted;
    }

    public boolean isSlotEnded() {
        return slotEnded;
    }

    public void setSlotEnded(boolean slotEnded) {
        this.slotEnded = slotEnded;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dhn.pojo.Booking[ bookingId=" + bookingId + " ]";
    }

}
