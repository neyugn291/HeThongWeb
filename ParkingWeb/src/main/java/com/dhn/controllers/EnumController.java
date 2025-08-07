/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.controllers;

import com.dhn.enums.UserRole;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dhngu
 */
@RestController
@RequestMapping("/api")
public class EnumController {

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok(
            Arrays.stream(UserRole.values())
                  .map(Enum::name)
                  .collect(Collectors.toList())
        );
    }
}

