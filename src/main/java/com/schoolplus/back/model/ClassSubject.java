package com.schoolplus.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "class_subject")
public class ClassSubject {
    @Id
    private String idclass;
    private String idsubject;
}
