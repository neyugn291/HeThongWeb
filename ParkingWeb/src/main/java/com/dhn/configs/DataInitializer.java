package com.dhn.configs;

import com.dhn.pojo.User;
import com.dhn.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
//        if (userService.getUserByUsername("admin123") == null) {
//            User u = new User();
//            u.setUsername("admin123");
//            u.setPassword("admin123");
//            u.setEmail("admin123@example.com");
//            u.setRole("admin");
//
//            userService.addUser(u);
//            System.out.println(">> Admin user created");
//        } else {
//            System.out.println(">> Admin user already exists");
//        }
    }
}
