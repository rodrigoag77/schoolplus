package com.schoolplus.back.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

import com.schoolplus.back.model.User;

@Getter
@Setter
public class UserDTO {
    private String id;

    private String login;

    @Column(name = "access_at")
    private Timestamp accessAt;

    public UserDTO(User user) {
        if (user == null)
            return;
        this.id = user.getId();
        this.login = user.getLogin();
        this.accessAt = user.getAccessAt();
    }
}
