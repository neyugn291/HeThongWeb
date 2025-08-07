/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.services.StatsService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dhngu
 */
@Controller
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public String parkingLotStats(Model model,
            @RequestParam(name = "day", required = false) Integer day,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "year", required = false) Integer year
    ) {
        List<Object[]> stats = statsService.statsRevenueByParkingLot();
        model.addAttribute("stats", stats);

        LocalDate today = LocalDate.now();
        int d = (day != null) ? day : today.getDayOfMonth();
        int m = (month != null) ? month : today.getMonthValue();
        int y = (year != null) ? year : today.getYear();

        List<Object[]> timeStats = statsService.statsRevenueByTime(d, m, y);
        model.addAttribute("timeStats", timeStats);
        model.addAttribute("day", d);
        model.addAttribute("month", m);
        model.addAttribute("year", y);

        List<Object[]> topUsers = statsService.statsTopCustomersByRevenue(5);
        model.addAttribute("topUsers", topUsers);

        return "stats";
    }

}
