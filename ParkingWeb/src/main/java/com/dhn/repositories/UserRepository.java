/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.repositories;

import com.dhn.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhngu
 */
public interface UserRepository { 
    User getUserByUsername(String username);
    User getUserById(int id);
    public List<User> getUsers(Map<String, String> params);
    User addUser(User u);
    User updateUser(User u);
    boolean authenticate(String username, String password);
    void deleteUser(int id);
}
