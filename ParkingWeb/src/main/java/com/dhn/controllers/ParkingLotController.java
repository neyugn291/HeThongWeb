/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.Review;
import com.dhn.pojo.User;
import com.dhn.services.ParkingLotService;
import com.dhn.services.ReviewService;
import com.dhn.services.UserService;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dhngu
 */
@Controller
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/parkinglots")
    public String listParkingLots(Model model) {
        return "parkinglots";
    }

    @GetMapping("/parkinglot/{id}")
    public String parkingLotDetail(Model model, @PathVariable("id") int id) {
        ParkingLot lot = this.parkingLotService.getParkingLotWithReviews(id);
        model.addAttribute("parkingLot", lot);
        return "parkinglot-details";
    }

    @GetMapping("/parkinglot/add")
    public String showAddParkingLotForm(Model model) {
        model.addAttribute("parkingLot", new ParkingLot());
        return "add-parkinglot";
    }

    @PostMapping("/parkinglot/add")
    public String addParkingLot(@ModelAttribute(value = "parkingLot") ParkingLot p) {
        this.parkingLotService.addOrUpdateParkingLot(p);
        return "redirect:/parkinglots";
    }

    @GetMapping("/parkinglot/update/{lotId}")
    public String updateParkinglot(Model model, @PathVariable(value = "lotId") int lotId) {
        model.addAttribute("parkingLot", this.parkingLotService.getParkingLotById(lotId));
        return "add-parkinglot";
    }

    @GetMapping("/parkinglot/{lotId}/reviews")
    public String listReviews(@PathVariable(value = "lotId") Integer lotId, Model model) {
        ParkingLot lot = parkingLotService.getParkingLotById(lotId);
        List<Review> reviews = reviewService.getReviewsByLotId(lotId);

        model.addAttribute("lot", lot);
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @PostMapping("/reviews/add")
    public String addReview(@ModelAttribute("review") Review review,
            @RequestParam("lotId") Integer lotId,
            Principal principal) {
        User u = this.userService.getUserByUsername(principal.getName());
        review.setUserId(u);
        ParkingLot lot = this.parkingLotService.getParkingLotById(lotId);
        review.setLotId(lot);
        this.reviewService.addReview(review);
        return "redirect:/";
    }

}
