package com.schoolplus.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "class")
public class Class {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
}
