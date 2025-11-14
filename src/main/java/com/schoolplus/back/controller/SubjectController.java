package com.schoolplus.back.controller;

import com.schoolplus.back.model.Subject;
import com.schoolplus.back.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjecties")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<Subject>> getAll() {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getById(@PathVariable("id") String id) {
        return subjectService.findById(id);
    }

    @PostMapping
    public ResponseEntity<List<Subject>> createList(@RequestBody List<Subject> subjects) {
        return subjectService.create(subjects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> update(@PathVariable("id") String id, @RequestBody Subject subject) {
        return subjectService.update(id, subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        return subjectService.deleteById(id);
    }
}
