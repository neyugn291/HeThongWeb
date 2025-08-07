/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.dhn.pojo.Invoice;
import com.dhn.repositories.InvoiceRepository;
import com.dhn.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dhngu
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice addInvoice(Invoice invoice) {
        return this.invoiceRepository.addInvoice(invoice);
    }
    
    @Override
    public void deleteInvoice(int id) {
        this.invoiceRepository.deleteInvoice(id);
    }
    
}
