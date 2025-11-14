package com.schoolplus.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Convert(converter = MemberTypeConverter.class)
    @Column(name = "type", nullable = false)
    private MemberType type;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    public Member(Member other) {
        if (other == null)
            return;
        this.type = other.type;
        this.name = other.name;
        this.email = other.email;
        this.nationalId = other.nationalId;
        this.phoneNumber = other.phoneNumber;
        this.user = other.user;
        this.address = other.address;
    }
}
