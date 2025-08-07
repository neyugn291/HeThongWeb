/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhn.services;

import com.dhn.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dhngu
 */
@Service("userDetailsService")
public interface UserService extends UserDetailsService {

    User getUserByUsername(String username);
    public List<User> getUsers(Map<String, String> params);
    User addUser(Map<String, String> info, MultipartFile avatar);
    User updateUser(Map<String, String> info, MultipartFile avatar, int id);
    boolean authenticate(String username, String password);
    void deleteUser(int id);
}
