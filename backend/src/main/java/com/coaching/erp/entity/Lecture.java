package com.coaching.erp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    private String subject;

    private String batch;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
private Long branchId;
    private Double duration; // in hours

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff teacher;
    @Enumerated(EnumType.STRING)
private LectureStatus status;
}