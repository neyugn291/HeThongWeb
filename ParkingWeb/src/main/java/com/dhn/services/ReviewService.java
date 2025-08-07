/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.services;

import com.dhn.pojo.Review;
import java.util.List;

/**
 *
 * @author dhngu
 */
public interface ReviewService {
    List<Review> getReviewsByLotId(int lotId);
    void addReview(Review review);
    void deleteReview(int id);
}
