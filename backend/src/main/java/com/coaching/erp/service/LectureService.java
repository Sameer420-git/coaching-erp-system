package com.coaching.erp.service;

import com.coaching.erp.entity.*;
import com.coaching.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private StaffRepository staffRepository;

    // 🔹 CREATE LECTURE (NO SALARY UPDATE)
    public Lecture addLecture(Long staffId, Lecture lecture) {

        Staff teacher = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        lecture.setTeacher(teacher);

        // ✅ Calculate duration
        long minutes = java.time.Duration.between(
                lecture.getStartTime(),
                lecture.getEndTime()
        ).toMinutes();

        double hours = minutes / 60.0;
        lecture.setDuration(hours);

        // ✅ Default status
        lecture.setStatus(LectureStatus.SCHEDULED);

        // ❌ DO NOT update salary here

        return lectureRepository.save(lecture);
    }

    // 🔹 COMPLETE LECTURE (SALARY UPDATE HERE)
    public Lecture completeLecture(Long lectureId) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        // Prevent duplicate completion
        if (lecture.getStatus() == LectureStatus.COMPLETED) {
            throw new RuntimeException("Lecture already completed");
        }

        Staff teacher = lecture.getTeacher();

        // ✅ Update teacher hours
        if (teacher.getTotalHoursWorked() == null) {
            teacher.setTotalHoursWorked(0.0);
        }

        teacher.setTotalHoursWorked(
                teacher.getTotalHoursWorked() + lecture.getDuration()
        );

        // ✅ Update salary ONLY for TEACHER
        if (teacher.getRole() == StaffRole.TEACHER) {
            teacher.setTotalSalary(
                    teacher.getSalaryPerHour() * teacher.getTotalHoursWorked()
            );
        }

        // ✅ Update lecture status
        lecture.setStatus(LectureStatus.COMPLETED);

        // Save updates
        staffRepository.save(teacher);
        return lectureRepository.save(lecture);
    }
}