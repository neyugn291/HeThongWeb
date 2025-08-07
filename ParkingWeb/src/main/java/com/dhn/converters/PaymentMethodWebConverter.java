/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.converters;

/**
 *
 * @author dhngu
 */
import com.dhn.enums.PaymentMethod;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodWebConverter implements Converter<String, PaymentMethod> {
    @Override
    public PaymentMethod convert(String source) {
        return PaymentMethod.fromString(source);
    }
}
