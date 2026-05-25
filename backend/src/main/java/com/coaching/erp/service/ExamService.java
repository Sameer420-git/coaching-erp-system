package com.coaching.erp.service;

import com.coaching.erp.entity.*;
import com.coaching.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Marks addMarks(Long studentId, Long examId, Double marks) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Marks m = new Marks();
        m.setStudent(student);
        m.setExam(exam);
        m.setObtainedMarks(marks);

        return marksRepository.save(m);
    }
}