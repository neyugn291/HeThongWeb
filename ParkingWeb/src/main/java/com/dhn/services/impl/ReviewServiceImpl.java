/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.dhn.pojo.Review;
import com.dhn.repositories.ReviewRepository;
import com.dhn.services.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dhngu
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;

    @Override
    public List<Review> getReviewsByLotId(int lotId) {
        return reviewRepo.getReviewsByLotId(lotId);
    }
    
    @Override
    public void addReview(Review review) {
        this.reviewRepo.addReview(review);
    }
    
    @Override
    public void deleteReview(int id) {
        this.reviewRepo.deleteReview(id);
    }

}
