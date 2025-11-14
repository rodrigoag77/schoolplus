package com.schoolplus.back.service;

import com.schoolplus.back.dto.UserDTO;
import com.schoolplus.back.model.User;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<User> findById(String id);

    ResponseEntity<List<User>> findAll();

    ResponseEntity<UserDTO> create(User user);

    ResponseEntity<UserDTO> update(String id, User userPut);

    ResponseEntity<Void> deleteById(String id);

    void updateAccessDate(String email);
}
