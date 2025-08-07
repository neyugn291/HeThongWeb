/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.repositories;

import com.dhn.pojo.Payment;

/**
 *
 * @author dhngu
 */
public interface PaymentRepository {
    public void addPayment(Payment p);
    public void updatePayment(Payment p);
}
