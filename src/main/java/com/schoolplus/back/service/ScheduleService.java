package com.schoolplus.back.service;

import com.schoolplus.back.dto.ScheduleDTO;
import com.schoolplus.back.model.Schedule;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ScheduleService {
  ResponseEntity<Void> deleteById(String id);
  ResponseEntity<ScheduleDTO> getById(String id);
  ResponseEntity<List<ScheduleDTO>> getAll();
  ResponseEntity<ScheduleDTO> create(Schedule Schedule);
  ResponseEntity<ScheduleDTO> update(String id, Schedule member);
}
