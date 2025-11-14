package com.schoolplus.back.repository;

import com.schoolplus.back.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, String> {
}
