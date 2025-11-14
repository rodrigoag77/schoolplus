package com.schoolplus.back.controller;

import com.schoolplus.back.dto.AuthDTO;
import com.schoolplus.back.repository.UserRepository;
import com.schoolplus.back.security.TokenService;
import com.schoolplus.back.security.UserDetail;
import com.schoolplus.back.security.jwtTokenData;
import com.schoolplus.back.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {
  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody AuthDTO user) {
    var authToken = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());
    var authentication = manager.authenticate(authToken);
    UserDetail response = (UserDetail) authentication.getPrincipal();
    var jwtToken = tokenService.buildToken(response);
    
    var userData = userRepository.findByLogin(user.getLogin());
    
    userService.updateAccessDate(user.getLogin());
    
    var jwt = new jwtTokenData(userData.getId(), user.getLogin(), jwtToken, userData.getAccessAt());

    return ResponseEntity.ok(jwt);
  }

}
