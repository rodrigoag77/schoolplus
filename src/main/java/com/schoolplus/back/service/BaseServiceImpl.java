package com.schoolplus.back.service;

import com.schoolplus.back.exception.ServiceException;

import lombok.NonNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public abstract class BaseServiceImpl<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public ResponseEntity<T> findById(@NonNull ID id) {
        try {
            return getRepository().findById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new ServiceException("Error finding by ID: " + e.getMessage());
        }
    }

    public ResponseEntity<List<T>> findAll() {
        try {
            List<T> items = getRepository().findAll();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            throw new ServiceException("Error listing entities: " + e.getMessage());
        }
    }

    public ResponseEntity<T> save(@NonNull T entity) {
        try {
            T saved = Objects.requireNonNull(getRepository().save(entity));
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            throw new ServiceException("Error saving entity: " + e.getMessage());
        }
    }

    public ResponseEntity<Void> deleteById(@NonNull ID id) {
        try {
            if (!getRepository().existsById(id))
                return ResponseEntity.notFound().build();
            getRepository().deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ServiceException("Error deleting entity: " + e.getMessage());
        }
    }

    public ResponseEntity<T> update(@NonNull ID id, @NonNull T entity) {
        try {
            var existing = getRepository().findById(id).orElse(null);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            updateIfNotNullOrEmpty(existing, entity);
            T updated = getRepository().save(existing);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            throw new ServiceException("Error updating entity: " + e.getMessage());
        }
    }

    protected void updateIfNotNullOrEmpty(T target, T source) {
        try {
            Field[] fields = target.getClass().getDeclaredFields();
            for (Field field : fields) {
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
