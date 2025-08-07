/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.configs;

import com.dhn.converters.BookingStatusWebConverter;
import com.dhn.converters.InvoiceStatusWebConverter;
import com.dhn.converters.PaymentMethodWebConverter;
import com.dhn.converters.SlotStatusWebConverter;
import com.dhn.formatters.LicensePlateFormatter;
import com.dhn.formatters.ParkingLotFormatter;
import com.dhn.formatters.ParkingSlotFormatter;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author dhngu
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableScheduling
@ComponentScan(basePackages = {
    "com.dhn.controllers",
    "com.dhn.repositories",
    "com.dhn.services",
    "com.dhn.configs"
})
public class WebAppContextConfigs implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new ParkingSlotFormatter());
        registry.addFormatter(new ParkingLotFormatter());
        registry.addFormatter(new LicensePlateFormatter());

        
        registry.addConverter(new BookingStatusWebConverter());
        registry.addConverter(new SlotStatusWebConverter());
        registry.addConverter(new InvoiceStatusWebConverter());
        registry.addConverter(new PaymentMethodWebConverter());

    }
    
    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        System.out.println(">>> Timezone set to Asia/Ho_Chi_Minh");
    }
}
