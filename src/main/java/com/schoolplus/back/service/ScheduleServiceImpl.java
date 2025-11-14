package com.schoolplus.back.service;

import com.schoolplus.back.model.Schedule;
import com.schoolplus.back.repository.ScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolplus.back.dto.ScheduleDTO;
import com.schoolplus.back.exception.ServiceException;

import lombok.NonNull;

import java.util.List;

@Service
public class ScheduleServiceImpl extends BaseDTOServiceImpl<Schedule, ScheduleDTO, String> implements ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Override
  protected JpaRepository<Schedule, String> getRepository() {
    return scheduleRepository;
  }

  @Override
  protected ScheduleDTO toDTO(Schedule entity) {
    return new ScheduleDTO(entity, entity.getClasses(), entity.getSubject(), entity.getMember());
  }

  @Override
  protected Schedule toEntity(ScheduleDTO dto) {
    // Implementação básica - pode ser estendida conforme necessário
    throw new UnsupportedOperationException("Conversão de DTO para entidade não suportada");
  }

  @Override
  @Transactional
  public ResponseEntity<Void> deleteById(@NonNull String id) {
    try {
      if (!scheduleRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
      }
      scheduleRepository.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      throw new ServiceException("Error deleting schedule: " + e.getMessage());
    }
  }

  @Override
  public ResponseEntity<ScheduleDTO> getById(@NonNull String id) {
    try {
      return scheduleRepository.findByIdWithDetails(id)
          .map(this::toDTO)
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Exception e) {
      throw new ServiceException("Error finding schedule: " + e.getMessage());
    }
  }

  @Override
  public ResponseEntity<List<ScheduleDTO>> getAll() {
    try {
      List<Schedule> schedules = scheduleRepository.findAllWithDetails();
      List<ScheduleDTO> scheduleDTOs = schedules.stream()
          .map(this::toDTO)
          .toList();
      return ResponseEntity.ok(scheduleDTOs);
    } catch (Exception e) {
      throw new ServiceException("Error listing schedules: " + e.getMessage());
    }
  }

  @Override
  @Transactional
  @SuppressWarnings("null")
  public ResponseEntity<ScheduleDTO> update(@NonNull String id, @NonNull Schedule schedule) {
    try {

      Schedule existingSchedule = scheduleRepository.findByIdWithDetails(id)
          .orElseThrow(() -> new ServiceException("Schedule not found"));

      updateIfNotNullOrEmpty(existingSchedule, schedule);
      Schedule updatedSchedule = scheduleRepository.save(existingSchedule);
      Schedule result = scheduleRepository.findByIdWithDetails(updatedSchedule.getId())
          .orElse(updatedSchedule);

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(toDTO(result));
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException("Error updating schedule: " + e.getMessage());
    }
  }

  @Override
  @Transactional
  public ResponseEntity<ScheduleDTO> create(@NonNull Schedule schedule) {
    try {
      
      Schedule createdSchedule = scheduleRepository.save(schedule);
      Schedule result = scheduleRepository.findByIdWithDetails(createdSchedule.getId())
          .orElse(createdSchedule);

      return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(result));
    } catch (Exception e) {
      throw new ServiceException("Error creating schedule: " + e.getMessage());
    }
  }
}
