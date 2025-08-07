/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.converters;

/**
 *
 * @author dhngu
 */
import com.dhn.enums.SlotStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SlotStatusWebConverter implements Converter<String, SlotStatus> {
    @Override
    public SlotStatus convert(String source) {
        return SlotStatus.fromString(source);
    }
}
