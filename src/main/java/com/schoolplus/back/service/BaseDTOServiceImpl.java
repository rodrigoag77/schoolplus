package com.schoolplus.back.service;

import com.schoolplus.back.exception.ServiceException;

import lombok.NonNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public abstract class BaseDTOServiceImpl<T, DTO, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    protected abstract DTO toDTO(T entity);

    public ResponseEntity<DTO> findById(@NonNull ID id) {
        try {
            return getRepository().findById(id)
                    .map(this::toDTO)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new ServiceException("Error finding by ID: " + e.getMessage());
        }
    }

    protected void updateIfNotNullOrEmpty(T target, T source) {
        try {
            java.lang.reflect.Field[] fields = target.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                if (isIdField(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException("Error updating fields: " + e.getMessage());
        }
    }

    private boolean isIdField(String fieldName) {
        return fieldName.equalsIgnoreCase("id");
    }

    protected void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }
}
