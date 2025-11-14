package com.schoolplus.back.controller;

import com.schoolplus.back.model.Department;
import com.schoolplus.back.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable("id") String id) {
        return departmentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<List<Department>> createList(@RequestBody List<Department> departments) {
        return departmentService.create(departments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable("id") String id, @RequestBody Department department) {
        return departmentService.update(id, department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        return departmentService.deleteById(id);
    }
}
