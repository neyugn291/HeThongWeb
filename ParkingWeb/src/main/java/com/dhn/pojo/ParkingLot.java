/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dhngu
 */
@Entity
@Table(name = "parking_lot")
@NamedQueries({
    @NamedQuery(name = "ParkingLot.findAll", query = "SELECT p FROM ParkingLot p"),
    @NamedQuery(name = "ParkingLot.findByLotId", query = "SELECT p FROM ParkingLot p WHERE p.lotId = :lotId"),
    @NamedQuery(name = "ParkingLot.findByName", query = "SELECT p FROM ParkingLot p WHERE p.name = :name"),
    @NamedQuery(name = "ParkingLot.findByAddress", query = "SELECT p FROM ParkingLot p WHERE p.address = :address"),
    @NamedQuery(name = "ParkingLot.findByTotalSlots", query = "SELECT p FROM ParkingLot p WHERE p.totalSlots = :totalSlots"),
    @NamedQuery(name = "ParkingLot.findByPricePerHour", query = "SELECT p FROM ParkingLot p WHERE p.pricePerHour = :pricePerHour")})
public class ParkingLot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lot_id")
    private Integer lotId;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "total_slots")
    private Integer totalSlots;
    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;
    @Lob
    @Column(name = "amenities")
    private String amenities;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "img")
    private String img;
    @OneToMany(mappedBy = "lotId")
    @JsonIgnore
    private Set<Review> reviewSet;
    @OneToMany(mappedBy = "lotId")
    @JsonIgnore
    private Set<ParkingSlot> parkingSlotSet;
    
    @Transient
    private MultipartFile file;

    public ParkingLot() {
    }

    public ParkingLot(Integer lotId) {
        this.lotId = lotId;
    }

    public Integer getLotId() {
        return lotId;
    }

    public void setLotId(Integer lotId) {
        this.lotId = lotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(Integer totalSlots) {
        this.totalSlots = totalSlots;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Set<Review> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(Set<Review> reviewSet) {
        this.reviewSet = reviewSet;
    }
    
    public Set<ParkingSlot> getParkingSlotSet() {
        return parkingSlotSet;
    }

    public void setParkingSlotSet(Set<ParkingSlot> parkingSlotSet) {
        this.parkingSlotSet = parkingSlotSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lotId != null ? lotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParkingLot)) {
            return false;
        }
        ParkingLot other = (ParkingLot) object;
        if ((this.lotId == null && other.lotId != null) || (this.lotId != null && !this.lotId.equals(other.lotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dhn.pojo.ParkingLot[ lotId=" + lotId + " ]";
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
