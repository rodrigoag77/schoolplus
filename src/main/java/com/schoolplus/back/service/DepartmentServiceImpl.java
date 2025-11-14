package com.schoolplus.back.service;

import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.model.Department;
import com.schoolplus.back.repository.DepartmentRepository;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, String> implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    protected JpaRepository<Department, String> getRepository() {
        return departmentRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<List<Department>> create(@NonNull List<Department> departments) {
        try {
            List<Department> savedDepartments = departments.stream()
                    .map(departmentRepository::save)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(savedDepartments);
        } catch (Exception e) {
            throw new ServiceException("Error creating departments: " + e.getMessage());
        }
    }
}
