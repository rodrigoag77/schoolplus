package com.schoolplus.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "address")
@NoArgsConstructor
public class Address {
    @Id
    private String id = UUID.randomUUID().toString();

    private String street;
    private Integer streetNumber;
    private String apartment;
    private String neighborhood;
    private String zipCode;

    @OneToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public Address(Address address) {
        this.id = address.id;
        this.street = address.street;
        this.streetNumber = address.streetNumber;
        this.apartment = address.apartment;
        this.neighborhood = address.neighborhood;
        this.zipCode = address.zipCode;
        this.city = address.city;
    }
}
