package com.schoolplus.back.service;

import com.schoolplus.back.dto.CityDTO;
import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.model.City;
import com.schoolplus.back.repository.CityRepository;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl extends BaseDTOServiceImpl<City, CityDTO, String> implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    protected JpaRepository<City, String> getRepository() {
        return cityRepository;
    }

    @Override
    protected CityDTO toDTO(City entity) {
        return new CityDTO(entity);
    }

    @Override
    public ResponseEntity<List<CityDTO>> findAll() {
        try {
            List<City> cities = cityRepository.findAll();
            List<CityDTO> dtos = cities.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            throw new ServiceException("Error listing cities: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<CityDTO>> create(@NonNull List<City> cities) {
        try {
            List<CityDTO> savedCities = cities.stream()
                    .map(cityRepository::save)
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(savedCities);
        } catch (Exception e) {
            throw new ServiceException("Error creating cities: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public ResponseEntity<CityDTO> update(@NonNull String id, @NonNull City city) {
        try {

            City existing = cityRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("City not found"));

            updateIfNotNullOrEmpty(existing, city);

            City updated = cityRepository.save(existing);
            return ResponseEntity.ok(toDTO(updated));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error updating city: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> deleteById(@NonNull String id) {
        try {
            if (!cityRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            cityRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ServiceException("Error deleting city: " + e.getMessage());
        }
    }
}
