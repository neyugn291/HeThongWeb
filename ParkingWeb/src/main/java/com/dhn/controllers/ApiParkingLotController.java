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
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dhngu
 */
@RestController
@RequestMapping("/api")
public class ApiParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private UserService userService;
    
    @DeleteMapping("/parkinglot/delete/{lotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingLot(@PathVariable(value = "lotId") int lotId) {
        this.parkingLotService.deleteParkingLot(lotId);
    }

    @GetMapping("/parkinglots")
    @CrossOrigin
    public ResponseEntity<List<ParkingLot>> list(@RequestParam Map<String, String> params) {
        System.out.println("Params: " + params);

        return new ResponseEntity<>(this.parkingLotService.getParkingLots(params), HttpStatus.OK);
    }

    @GetMapping("/parkinglot/{lotId}")
    @CrossOrigin
    public ResponseEntity<ParkingLot> retrieve(@PathVariable(value = "lotId") int lotId) {
        return new ResponseEntity<>(this.parkingLotService.getParkingLotById(lotId), HttpStatus.OK);
    }
    
    
    @DeleteMapping("/review/delete/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable(value = "reviewId") int reviewId) {
        this.reviewService.deleteReview(reviewId);
    }
    
    @GetMapping("/reviews")
    @CrossOrigin
    public ResponseEntity<List<Review>> getReviewsByLotId(@RequestParam("lotId") Integer lotId) {
        
        List<Review> reviews = reviewService.getReviewsByLotId(lotId);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviews);
    }
    
    @PostMapping("/secure/review/add")
    @CrossOrigin
    public ResponseEntity<?> createBooking(@RequestBody Review r, Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        r.setUserId(u);
        reviewService.addReview(r);
        return ResponseEntity.ok("Gui danh gia thanh cong");
    }
}
