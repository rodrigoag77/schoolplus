package com.schoolplus.back.repository;

import com.schoolplus.back.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
