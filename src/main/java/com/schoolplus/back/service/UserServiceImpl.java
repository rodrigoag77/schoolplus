package com.schoolplus.back.service;

import com.schoolplus.back.dto.UserDTO;
import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.exception.UserAlreadyExistsException;
import com.schoolplus.back.model.User;
import com.schoolplus.back.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<User> findById(@NonNull String id) {
        try {
            return userRepository.findById(id)
                    .map(user -> {
                        user.setPassword(null);
                        return ResponseEntity.ok(user);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new ServiceException("Error finding user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<User>> findAll() {
        try {
            List<User> users = userRepository.findAll();
            users.forEach(u -> u.setPassword(null));
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new ServiceException("Error listing users: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> create(@NonNull User user) {
        try {
            validateNotNull(user.getLogin(), "Login cannot be null");

            User existingUser = userRepository.findByLogin(user.getLogin());
            if (existingUser != null && existingUser.getId() != null) {
                throw new UserAlreadyExistsException();
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(new UserDTO(savedUser));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error creating user: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public ResponseEntity<UserDTO> update(@NonNull String id, @NonNull User userPut) {
        try {

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("User not found"));

            updateUserFields(user, userPut);
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(new UserDTO(updatedUser));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error updating user: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteById(@NonNull String id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ServiceException("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateAccessDate(@NonNull String login) {
        try {

            User user = userRepository.findByLogin(login);
            if (user != null) {
                user.setAccessAt(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new ServiceException("Error updating access date: " + e.getMessage());
        }
    }

    private void updateUserFields(User user, User userUpdate) {
        if (userUpdate.getLogin() != null && !userUpdate.getLogin().isEmpty()) {
            user.setLogin(userUpdate.getLogin());
        }
        if (userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
    }

    private void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }
}
