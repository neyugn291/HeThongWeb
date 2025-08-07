/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.cloudinary.Cloudinary;
import com.dhn.pojo.User;
import com.dhn.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dhngu
 */
@Controller
public class UserController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String addPage() {
        return "add-user";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", this.userService.getUsers(null));
        return "users";
    }

    @PostMapping("/register")
    public String addUser(@RequestParam Map<String, String> params,
            @RequestParam("avatar") MultipartFile avatar,
            Model model) {
        this.userService.addUser(params, avatar);
        return "redirect:/login";
    }

    // Xử lý POST cập nhật người dùng
    @PostMapping("/user/update/{userId}")
    public String updateUser(@RequestParam Map<String, String> params,
            @RequestParam(name = "avatar", required = false) MultipartFile avatar,
            @PathVariable(value = "userId") int userId,
            Model model) {
        this.userService.updateUser(params, avatar, userId);
        return "redirect:/users"; // chuyển hướng lại trang danh sách
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user-details";
    }


}
