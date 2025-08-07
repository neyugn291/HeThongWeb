/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.repositories;

import com.dhn.pojo.Invoice;

/**
 *
 * @author dhngu
 */
public interface InvoiceRepository {

    Invoice addInvoice(Invoice invoice);
    
    public void updateInvoice(Invoice i);

    void deleteInvoice(int id);
}
