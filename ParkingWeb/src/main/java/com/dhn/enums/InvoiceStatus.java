/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 *
 * @author dhngu
 */
public enum InvoiceStatus {
    PAID,
    UNPAID,
    REFUNDED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static InvoiceStatus fromString(String dbValue) {
        return InvoiceStatus.valueOf(dbValue.toUpperCase());
    }

    @Converter(autoApply = true)
    public static class InvoiceStatusConverter implements AttributeConverter<InvoiceStatus, String> {
        @Override
        public String convertToDatabaseColumn(InvoiceStatus status) {
            return status != null ? status.toString() : null;
        }

        @Override
        public InvoiceStatus convertToEntityAttribute(String dbValue) {
            return dbValue != null ? InvoiceStatus.fromString(dbValue) : null;
        }
    }
}
