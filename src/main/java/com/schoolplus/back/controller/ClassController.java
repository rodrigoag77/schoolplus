package com.schoolplus.back.controller;

import com.schoolplus.back.model.Class;
import com.schoolplus.back.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<Class>> getAll() {
        return classService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Class> getById(@PathVariable("id") String id) {
        return classService.findById(id);
    }

    @PostMapping
    public ResponseEntity<List<Class>> createList(@RequestBody List<Class> classes) {
        return classService.create(classes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Class> update(@PathVariable("id") String id, @RequestBody Class localClass) {
        return classService.update(id, localClass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        return classService.deleteById(id);
    }
}
