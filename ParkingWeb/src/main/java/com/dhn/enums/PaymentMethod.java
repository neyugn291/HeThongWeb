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
public enum PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    E_WALLET,
    BANK_TRANSFER,
    CASH;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static PaymentMethod fromString(String dbValue) {
        return PaymentMethod.valueOf(dbValue.toUpperCase());
    }

    @Converter(autoApply = true)
    public static class PaymentMethodConverter implements AttributeConverter<PaymentMethod, String> {
        @Override
        public String convertToDatabaseColumn(PaymentMethod status) {
            return status != null ? status.toString() : null;
        }

        @Override
        public PaymentMethod convertToEntityAttribute(String dbValue) {
            return dbValue != null ? PaymentMethod.fromString(dbValue) : null;
        }
    }
}
