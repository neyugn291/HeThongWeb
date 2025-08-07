/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.pojo;

import com.dhn.enums.SlotStatus;
import com.dhn.enums.SlotStatus.SlotStatusConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
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
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author dhngu
 */
@Entity
@Table(name = "parking_slot")
@NamedQueries({
    @NamedQuery(name = "ParkingSlot.findAll", query = "SELECT p FROM ParkingSlot p"),
    @NamedQuery(name = "ParkingSlot.findBySlotId", query = "SELECT p FROM ParkingSlot p WHERE p.slotId = :slotId"),
    @NamedQuery(name = "ParkingSlot.findBySlotCode", query = "SELECT p FROM ParkingSlot p WHERE p.slotCode = :slotCode"),
    @NamedQuery(name = "ParkingSlot.findByStatus", query = "SELECT p FROM ParkingSlot p WHERE p.status = :status")})
public class ParkingSlot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "slot_id")
    private Integer slotId;
    @Column(name = "slot_code")
    private String slotCode;
    @Column(name = "status")
    @Convert(converter = SlotStatusConverter.class)
    private SlotStatus status;
    @OneToMany(mappedBy = "slotId")
    @JsonIgnore
    private Set<Booking> bookingSet;
    @JoinColumn(name = "lot_id", referencedColumnName = "lot_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ParkingLot lotId;

    public ParkingSlot() {
    }

    public ParkingSlot(Integer slotId) {
        this.slotId = slotId;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getSlotCode() {
        return slotCode;
    }

    public void setSlotCode(String slotCode) {
        this.slotCode = slotCode;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public Set<Booking> getBookingSet() {
        return bookingSet;
    }

    public void setBookingSet(Set<Booking> bookingSet) {
        this.bookingSet = bookingSet;
    }

    public ParkingLot getLotId() {
        return lotId;
    }

    public void setLotId(ParkingLot lotId) {
        this.lotId = lotId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (slotId != null ? slotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParkingSlot)) {
            return false;
        }
        ParkingSlot other = (ParkingSlot) object;
        if ((this.slotId == null && other.slotId != null) || (this.slotId != null && !this.slotId.equals(other.slotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dhn.pojo.ParkingSlot[ slotId=" + slotId + " ]";
    }

}
