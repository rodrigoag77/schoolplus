package com.schoolplus.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule {
    @Id
    private String id = UUID.randomUUID().toString();

    // keep raw id fields (used for inserts/updates)
    private String idclass;
    private String idsubject;
    private String idmember;

    // JPA relationships (read-only mapping via insertable=false, updatable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclass", referencedColumnName = "id", insertable = false, updatable = false)
    private Class classes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsubject", referencedColumnName = "id", insertable = false, updatable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmember", referencedColumnName = "id", insertable = false, updatable = false)
    private Member member;

    @Column(name = "recurrence_rule")
    private Date recurrenceRule;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
}
