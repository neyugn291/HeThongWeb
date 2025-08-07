/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.pojo.LicensePlate;
import com.dhn.pojo.Notification;
import com.dhn.pojo.User;
import com.dhn.services.LicensePlateService;
import com.dhn.services.NotificationService;
import com.dhn.services.UserService;
import com.dhn.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author dhngu
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LicensePlateService plateService;

    @PostMapping(path = "/user/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestParam Map<String, String> info, @RequestParam("avatar") MultipartFile avatar) {

        User u = this.userService.addUser(info, avatar);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userMap) {
        String username = userMap.get("username");
        String password = userMap.get("password");
        System.out.println(this.userService.authenticate(username, password));

        if (this.userService.authenticate(username, password)) {
            try {
                String token = JwtUtils.generateToken(username);
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") int userId, Principal principal) {
        String currentUsername = principal.getName();
        User currentUser = this.userService.getUserByUsername(currentUsername);
        if (currentUser.getUserId() == userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không thể tự xóa tài khoản của mình.");
        }
        this.userService.deleteUser(userId);
    }
    
    @PostMapping("/secure/plate/add")
    public ResponseEntity<?> addPlate(@RequestBody LicensePlate licensePlate, Principal principal) {
        String username = principal.getName();

        User u = this.userService.getUserByUsername(username);
        licensePlate.setUser(u);
        this.plateService.addPlate(licensePlate);
        return ResponseEntity.status(HttpStatus.CREATED).body(licensePlate);
    }
    
    @DeleteMapping("/plate/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicensePlate(@PathVariable(value = "id") int id) {
        this.plateService.deletePlate(id);
    }
    
    @Autowired
    NotificationService notiService;
    
    @GetMapping("/secure/notifications")
    public ResponseEntity<List<Notification>> getUserNotis(Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        return new ResponseEntity<>(this.notiService.getNotisByUsername(u.getUsername()), HttpStatus.OK);
    }
}
