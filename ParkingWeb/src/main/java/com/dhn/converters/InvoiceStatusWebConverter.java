/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.converters;

/**
 *
 * @author dhngu
 */
import com.dhn.enums.InvoiceStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceStatusWebConverter implements Converter<String, InvoiceStatus> {
    @Override
    public InvoiceStatus convert(String source) {
        return InvoiceStatus.fromString(source);
    }
}