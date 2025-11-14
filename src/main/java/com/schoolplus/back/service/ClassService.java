package com.schoolplus.back.service;

import com.schoolplus.back.model.Class;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ClassService {
    ResponseEntity<Class> findById(String id);
    ResponseEntity<List<Class>> findAll();
    ResponseEntity<List<Class>> create(List<Class> classes);
    ResponseEntity<Class> update(String id, Class classEntity);
    ResponseEntity<Void> deleteById(String id);
}
