package com.coaching.erp.dto;

import com.coaching.erp.entity.*;

import lombok.Data;

import java.util.List;

@Data
public class StudentDashboardDTO {

    private Student student;
    private List<Fee> fees;
    private List<Marks> marks;
    private List<Attendance> attendance;
    
    public Student getStudent() {
    return student;
}

public void setStudent(Student student) {
    this.student = student;
}

public List<Fee> getFees() {
    return fees;
}

public void setFees(List<Fee> fees) {
    this.fees = fees;
}

public List<Marks> getMarks() {
    return marks;
}

public void setMarks(List<Marks> marks) {
    this.marks = marks;
}

public List<Attendance> getAttendance() {
    return attendance;
}

public void setAttendance(List<Attendance> attendance) {
    this.attendance = attendance;
}

    // getters & setters
}