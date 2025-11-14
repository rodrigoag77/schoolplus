package com.schoolplus.back.service;

import com.schoolplus.back.dto.CityDTO;
import com.schoolplus.back.model.City;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CityService {
    ResponseEntity<CityDTO> findById(String id);

    ResponseEntity<List<CityDTO>> findAll();

    ResponseEntity<List<CityDTO>> create(List<City> cities);

    ResponseEntity<CityDTO> update(String id, City city);

    ResponseEntity<Void> deleteById(String id);
}
