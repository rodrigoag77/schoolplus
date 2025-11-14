package com.schoolplus.back.controller;

import com.schoolplus.back.dto.AuthDTO;
import com.schoolplus.back.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody AuthDTO user) {
    return authService.create(user);
  }
}
