package com.schoolplus.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @JsonIgnore
    private String id = UUID.randomUUID().toString();

    @Column
    private String login;

    @Column
    private String password;

    @Column(name = "access_at")
    private Timestamp accessAt;

    public User(User user) {
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.accessAt = user.accessAt;
    }
}
