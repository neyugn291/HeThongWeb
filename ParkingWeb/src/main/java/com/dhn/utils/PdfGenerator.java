/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.utils;

import com.dhn.pojo.Booking;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author dhngu
 */
public class PdfGenerator {

    public static String generateInvoiceAndSaveToFile(Booking booking, String outputDir) throws IOException {
    
        String fontPath = "C:/Windows/Fonts/tahoma.ttf";
        PdfFont font = PdfFontFactory.createFont(fontPath, "Identity-H", true);

        Files.createDirectories(Paths.get(outputDir));

 
        String fileName = "invoice-" + booking.getBookingId() + ".pdf";
        String filePath = outputDir + "/" + fileName;

      
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("HÓA ĐƠN").setFont(font).setBold().setFontSize(18));
        document.add(new Paragraph("Mã đơn: " + booking.getBookingId()).setFont(font));
        document.add(new Paragraph("Khách hàng: " + booking.getUserId().getFullName()).setFont(font));
        document.add(new Paragraph("Bãi đỗ xe: " + booking.getSlotId().getLotId().getName()).setFont(font));
        document.add(new Paragraph("Chỗ đỗ xe: " + booking.getSlotId().getSlotCode()).setFont(font));
        document.add(new Paragraph("Ngày đặt: " + booking.getStartTime().toString()).setFont(font));
        document.add(new Paragraph("Biển số xe: " + booking.getLicensePlateId().getLicensePlate()).setFont(font));
        document.add(new Paragraph("Tổng tiền: " + booking.getPayment().getAmount() + " VND").setFont(font));
        document.add(new Paragraph("\nCảm ơn quý khách!").setFont(font));

        document.close();

        return filePath;
    }

}
