/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.enums;

/**
 *
 * @author dhngu
 */

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum BookingStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static BookingStatus fromString(String dbValue) {
        return BookingStatus.valueOf(dbValue.toUpperCase());
    }

    @Converter(autoApply = true)
    public static class BookingStatusConverter implements AttributeConverter<BookingStatus, String> {
        @Override
        public String convertToDatabaseColumn(BookingStatus status) {
            return status != null ? status.toString() : null;
        }

        @Override
        public BookingStatus convertToEntityAttribute(String dbValue) {
            return dbValue != null ? BookingStatus.fromString(dbValue) : null;
        }
    }
}

