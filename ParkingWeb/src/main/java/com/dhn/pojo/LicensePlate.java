/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dhngu
 */
@Entity
@Table(name = "license_plates")
@NamedQueries({
    @NamedQuery(name = "LicensePlate.findAll", query = "SELECT l FROM LicensePlate l"),
    @NamedQuery(name = "LicensePlate.findById", query = "SELECT l FROM LicensePlate l WHERE l.id = :id"),
    @NamedQuery(name = "LicensePlate.findByLicensePlate", query = "SELECT l FROM LicensePlate l WHERE l.licensePlate = :licensePlate"),
    @NamedQuery(name = "LicensePlate.findByUserId", query = "SELECT l FROM LicensePlate l WHERE l.user.id = :userId"),
    @NamedQuery(name = "LicensePlate.findByUsername", query = "SELECT l FROM LicensePlate l WHERE l.user.username = :username")
})
public class LicensePlate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public LicensePlate() {
    }

    public LicensePlate(String licensePlate, User user) {
        this.licensePlate = licensePlate;
        this.user = user;
        this.createdAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LicensePlate{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}
