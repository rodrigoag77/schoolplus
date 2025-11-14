package com.schoolplus.back.dto;

import com.schoolplus.back.model.City;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {
    private String id;
    private String city;
    private String state;
    private String country;

    public CityDTO(City city) {
        if (city == null)
            return;
        this.id = city.getId();
        this.city = city.getCity();
        this.state = city.getState();
        this.country = city.getCountry();
    }
}
