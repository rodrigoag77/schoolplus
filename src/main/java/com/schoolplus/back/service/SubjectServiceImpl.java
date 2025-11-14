package com.schoolplus.back.service;

import com.schoolplus.back.exception.ServiceException;
import com.schoolplus.back.model.Subject;
import com.schoolplus.back.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl extends BaseServiceImpl<Subject, String> implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    protected JpaRepository<Subject, String> getRepository() {
        return subjectRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<List<Subject>> create(List<Subject> subjects) {
        try {
            validateNotNull(subjects, "Lista de disciplinas não pode ser nula");
            List<Subject> savedSubjects = subjects.stream()
                    .map(subjectRepository::save)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(savedSubjects);
        } catch (Exception e) {
            throw new ServiceException("Erro ao criar disciplinas: " + e.getMessage());
        }
    }
}
