/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.dhn.pojo.Notification;
import com.dhn.pojo.User;
import com.dhn.repositories.NotificationRepository;
import com.dhn.services.NotificationService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dhngu
 */
@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepo;

    @Override
    public void notify(User user, String message) {
        Notification noti = new Notification();
        noti.setUserId(user);
        noti.setMessage(message);
        noti.setSentAt(new Date());
        noti.setIsRead(false);
        this.notificationRepo.addNotification(noti);
    }

    @Override
    public List<Notification> getNotisByUsername(String username) {
        return this.notificationRepo.getNotisByUsername(username);
    }
    
    
}
