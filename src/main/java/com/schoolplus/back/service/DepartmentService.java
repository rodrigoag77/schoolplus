package com.schoolplus.back.service;

import com.schoolplus.back.model.Department;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface DepartmentService {
    ResponseEntity<Department> findById(String id);
    ResponseEntity<List<Department>> findAll();
    ResponseEntity<List<Department>> create(List<Department> departments);
    ResponseEntity<Department> update(String id, Department department);
    ResponseEntity<Void> deleteById(String id);
}
