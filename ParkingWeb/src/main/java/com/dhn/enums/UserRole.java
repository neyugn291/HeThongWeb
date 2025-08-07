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

public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static UserRole fromValue(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }

    @Converter(autoApply = false)
    public static class UserRoleConverter implements AttributeConverter<UserRole, String> {

        @Override
        public String convertToDatabaseColumn(UserRole attribute) {
            return attribute != null ? attribute.getValue() : null;
        }

        @Override
        public UserRole convertToEntityAttribute(String dbData) {
            return dbData != null ? UserRole.fromValue(dbData) : null;
        }
    }
}


