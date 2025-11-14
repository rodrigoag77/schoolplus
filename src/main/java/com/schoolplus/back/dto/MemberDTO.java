package com.schoolplus.back.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.schoolplus.back.model.Member;
import com.schoolplus.back.model.MemberType;
import com.schoolplus.back.model.MemberTypeConverter;

@Getter
@Setter
public class MemberDTO {
    private String id;

    @Column(name = "name")
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDTO user;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressDTO address;

    public MemberDTO(Member member) {
        if (member == null)
            return;
        this.id = member.getId();
        this.type = member.getType();
        this.name = member.getName();
        this.email = member.getEmail();
        this.nationalId = member.getNationalId();
        this.phoneNumber = member.getPhoneNumber();
        if (member.getUser() != null)
            this.user = new UserDTO(member.getUser());
        if (member.getAddress() != null)
            this.address = new AddressDTO(member.getAddress());
    }
}
