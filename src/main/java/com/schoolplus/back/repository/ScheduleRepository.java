package com.schoolplus.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolplus.back.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
  @Query("SELECT s FROM Schedule s LEFT JOIN FETCH s.classes LEFT JOIN FETCH s.subject LEFT JOIN FETCH s.member")
  List<Schedule> findAllWithDetails();

  @Query("SELECT s FROM Schedule s LEFT JOIN FETCH s.classes LEFT JOIN FETCH s.subject LEFT JOIN FETCH s.member WHERE s.id = :id")
  Optional<Schedule> findByIdWithDetails(@Param("id") String id);
}
