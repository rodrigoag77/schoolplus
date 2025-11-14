package com.schoolplus.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "city")
public class City {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String city;

    private String state;
    private String country;
}
