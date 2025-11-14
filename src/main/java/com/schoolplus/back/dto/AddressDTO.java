package com.schoolplus.back.dto;

import com.schoolplus.back.model.Address;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String id;

    private String street;
    private Integer streetNumber;
    private String apartment;
    private String neighborhood;
    private String zipCode;

    @OneToOne
    @JoinColumn(name = "city_id")
    private CityDTO city;

    public AddressDTO(Address address) {
        if (address == null)
            return;
        this.id = address.getId();
        this.street = address.getStreet();
        this.streetNumber = address.getStreetNumber();
        this.apartment = address.getApartment();
        this.neighborhood = address.getNeighborhood();
        this.zipCode = address.getZipCode();
        this.city = new CityDTO(address.getCity());
    }
}
