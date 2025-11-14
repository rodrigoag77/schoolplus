package com.schoolplus.back.repository;

import com.schoolplus.back.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, String> {
}
