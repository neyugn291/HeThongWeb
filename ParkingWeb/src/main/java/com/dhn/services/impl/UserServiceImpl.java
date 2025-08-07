/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dhn.enums.UserRole;
import com.dhn.pojo.User;
import com.dhn.repositories.UserRepository;

import com.dhn.services.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dhngu
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public User addUser(Map<String, String> info, MultipartFile avatar) {
        User u = new User();

        u.setUsername(info.get("username"));
        u.setEmail(info.get("email"));
        u.setFullName(info.get("fullName"));
        u.setPhone(info.get("phone"));
        u.setPassword(passwordEncoder.encode(info.get("password")));

        u.setRole(UserRole.USER);
        u.setTwoFactorEnabled(Boolean.FALSE);
        u.setCreatedAt(new Date());

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(uploadResult.get("secure_url").toString());
            } catch (IOException ex) {
                throw new RuntimeException("Lỗi khi upload avatar lên Cloudinary", ex);
            }
        }

        return this.userRepo.addUser(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> [DEBUG] Đang gọi loadUserByUsername với: " + username);
        User u = this.getUserByUsername(username);

        System.out.println(">>> [DEBUG] Đang kiểm tra user: " + username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + u.getRole().toString()));
        System.out.println("Encoded in DB: " + u.getPassword());
        System.out.println("Matches? " + passwordEncoder.matches("admin123", u.getPassword()));
        System.out.println(u.getRole());

        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public void deleteUser(int id) {
        this.userRepo.deleteUser(id);
    }

    @Override
    @Transactional
    public List<User> getUsers(Map<String, String> params) {
        return this.userRepo.getUsers(params);
    }

    @Override
    public User updateUser(Map<String, String> info, MultipartFile avatar, int id) {
        User u = this.userRepo.getUserById(id);

        u.setUsername(info.get("username"));
        u.setEmail(info.get("email"));
        u.setFullName(info.get("fullName"));
        u.setPhone(info.get("phone"));

        String roleStr = info.get("role");
        if (roleStr != null && !roleStr.isEmpty()) {
            u.setRole(UserRole.fromValue(roleStr)); // hoặc dùng valueOf(roleStr.toUpperCase())
        }

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(uploadResult.get("secure_url").toString());
            } catch (IOException ex) {
                throw new RuntimeException("Lỗi khi upload avatar lên Cloudinary", ex);
            }
        }

        return this.userRepo.updateUser(u);
    }

}
