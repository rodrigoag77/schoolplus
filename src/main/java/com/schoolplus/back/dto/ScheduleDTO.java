package com.schoolplus.back.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

import com.schoolplus.back.model.Class;
import com.schoolplus.back.model.Member;
import com.schoolplus.back.model.MemberSummary;
import com.schoolplus.back.model.Schedule;
import com.schoolplus.back.model.Subject;

@Getter
@Setter
public class ScheduleDTO {
    private String id;

    private Date recurrenceRule;
    
    private Date startDate;
    
    private Date startTime;
    
    private Date endDate;
    
    private Date endTime;
    
    private Timestamp createdAt;
    
    private Timestamp updatedAt;

    private Integer dayOfWeek;

    private MemberSummary member;

    private Class classes;

    private Subject subject;

    public ScheduleDTO(Schedule s, Class classes, Subject subject, Member member) {
        if (s == null) return;
        this.id = s.getId();
        this.classes = classes;
        this.subject = subject;
        this.member = member != null ? new MemberSummary(member) : null;
        this.recurrenceRule = s.getRecurrenceRule();
        this.dayOfWeek = s.getDayOfWeek();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
        this.startTime = s.getStartTime();
        this.endTime = s.getEndTime();
        this.createdAt = s.getCreatedAt();
        this.updatedAt = s.getUpdatedAt();
    }
}
