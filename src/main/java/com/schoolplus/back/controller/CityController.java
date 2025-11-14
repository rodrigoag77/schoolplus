package com.schoolplus.back.controller;

import com.schoolplus.back.dto.CityDTO;
import com.schoolplus.back.model.City;
import com.schoolplus.back.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAll() {
        return cityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getById(@PathVariable("id") String id) {
        return cityService.findById(id);
    }

    @PostMapping
    public ResponseEntity<List<CityDTO>> createList(@RequestBody List<City> cities) {
        return cityService.create(cities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> update(@PathVariable("id") String id, @RequestBody City city) {
        return cityService.update(id, city);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        cityService.deleteById(id);
    }
}
