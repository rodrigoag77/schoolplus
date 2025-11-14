package com.schoolplus.back.controller;

import com.schoolplus.back.dto.ScheduleDTO;
import com.schoolplus.back.model.Schedule;
import com.schoolplus.back.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAll() {
        return scheduleService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getById(@PathVariable("id") String id) {
        return scheduleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> create(@RequestBody Schedule schedule) {
        return scheduleService.create(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> update(@PathVariable("id") String id, @RequestBody Schedule schedule) {
        return scheduleService.update(id, schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        return scheduleService.deleteById(id);
    }
}
