/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.repositories;

import com.dhn.pojo.Notification;
import java.util.List;

/**
 *
 * @author dhngu
 */
public interface NotificationRepository {

    public List<Notification> getNotification();

    public List<Notification> getNotisByUsername(String username);

    public void addNotification(Notification n);

    public void deleteNotification(int id);
}
