package com.schoolplus.back.service;

import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.model.Class;
import com.schoolplus.back.repository.ClassRepository;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl extends BaseServiceImpl<Class, String> implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Override
    protected JpaRepository<Class, String> getRepository() {
        return classRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<List<Class>> create(@NonNull List<Class> classes) {
        try {
            List<Class> savedClasses = classes.stream()
                    .map(classRepository::save)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(savedClasses);
        } catch (Exception e) {
            throw new ServiceException("Error creating classes: " + e.getMessage());
        }
    }
}
