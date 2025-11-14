package com.schoolplus.back.service;

import com.schoolplus.back.dto.AuthDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Object> create(@RequestBody AuthDTO user);
}
