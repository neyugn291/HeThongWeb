/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.pojo;

import com.dhn.enums.InvoiceStatus;
import com.dhn.enums.InvoiceStatus.InvoiceStatusConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author dhngu
 */
@Entity
@Table(name = "invoice")
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByInvoiceId", query = "SELECT i FROM Invoice i WHERE i.invoiceId = :invoiceId"),
    @NamedQuery(name = "Invoice.findByInvoiceDate", query = "SELECT i FROM Invoice i WHERE i.invoiceDate = :invoiceDate"),
    @NamedQuery(name = "Invoice.findByTotalAmount", query = "SELECT i FROM Invoice i WHERE i.totalAmount = :totalAmount"),
    @NamedQuery(name = "Invoice.findByStatus", query = "SELECT i FROM Invoice i WHERE i.status = :status")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoice_id")
    private Integer invoiceId;
    @Column(name = "invoice_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Convert(converter = InvoiceStatusConverter.class)
    @Column(name = "status")
    private InvoiceStatus status;
    @Lob
    @Column(name = "pdf_url")
    private String pdfUrl;
    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id")
    @JsonIgnoreProperties("invoiceId") 
    private Booking bookingId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public Invoice() {
    }

    public Invoice(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceId != null ? invoiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invoiceId == null && other.invoiceId != null) || (this.invoiceId != null && !this.invoiceId.equals(other.invoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dhn.pojo.Invoice[ invoiceId=" + invoiceId + " ]";
    }

}
