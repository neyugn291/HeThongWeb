/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.services;

import com.dhn.pojo.Notification;
import com.dhn.pojo.User;
import java.util.List;

/**
 *
 * @author dhngu
 */
public interface NotificationService {
    public void notify(User user, String message);
    public List<Notification> getNotisByUsername(String username);

}
