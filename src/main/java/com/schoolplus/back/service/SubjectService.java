package com.schoolplus.back.service;

import com.schoolplus.back.model.Subject;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface SubjectService {
    ResponseEntity<Subject> findById(String id);
    ResponseEntity<List<Subject>> findAll();
    ResponseEntity<List<Subject>> create(List<Subject> subjects);
    ResponseEntity<Subject> update(String id, Subject subject);
    ResponseEntity<Void> deleteById(String id);
}
