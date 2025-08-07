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
public enum SlotStatus {
    AVAILABLE,
    BOOKED,
    OCCUPIED,
    MAINTENANCE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static SlotStatus fromString(String dbValue) {
        return SlotStatus.valueOf(dbValue.toUpperCase());
    }

    // Converter cho JPA
    @Converter(autoApply = true)
    public static class SlotStatusConverter implements AttributeConverter<SlotStatus, String> {

        @Override
        public String convertToDatabaseColumn(SlotStatus status) {
            return status != null ? status.toString() : null;
        }

        @Override
        public SlotStatus convertToEntityAttribute(String dbValue) {
            return dbValue != null ? SlotStatus.fromString(dbValue) : null;
        }
    }
}
