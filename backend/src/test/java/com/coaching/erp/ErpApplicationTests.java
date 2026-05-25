package com.coaching.erp;

import com.coaching.erp.entity.Attendance;
import com.coaching.erp.entity.Exam;
import com.coaching.erp.entity.ExamType;
import com.coaching.erp.entity.Fee;
import com.coaching.erp.entity.Lecture;
import com.coaching.erp.entity.Marks;
import com.coaching.erp.entity.Role;
import com.coaching.erp.entity.Staff;
import com.coaching.erp.entity.StaffRole;
import com.coaching.erp.entity.Student;
import com.coaching.erp.entity.User;
import com.coaching.erp.repository.AttendanceRepository;
import com.coaching.erp.repository.ExamRepository;
import com.coaching.erp.repository.FeeRepository;
import com.coaching.erp.repository.LectureRepository;
import com.coaching.erp.repository.MarksRepository;
import com.coaching.erp.repository.StaffRepository;
import com.coaching.erp.repository.StudentRepository;
import com.coaching.erp.repository.UserRepository;
import com.coaching.erp.security.JwtUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ErpApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(userRepository).isNotNull();
        assertThat(studentRepository).isNotNull();
        assertThat(staffRepository).isNotNull();
        assertThat(examRepository).isNotNull();
        assertThat(feeRepository).isNotNull();
        assertThat(marksRepository).isNotNull();
        assertThat(attendanceRepository).isNotNull();
        assertThat(lectureRepository).isNotNull();
    }

    @Test
    void jpaMappingsAndRepositoryQueriesWorkTogether() {
        User user = createUser("student-one", Role.STUDENT);

        Student student = new Student();
        student.setName("Student One");
        student.setRollNo("student-one");
        student.setBranchId(1L);
        student.setUser(user);
        studentRepository.saveAndFlush(student);

        Staff teacher = new Staff();
        teacher.setName("teacher-one");
        teacher.setRole(StaffRole.TEACHER);
        teacher.setSalaryPerHour(500.0);
        staffRepository.saveAndFlush(teacher);

        Exam exam = new Exam();
        exam.setExamType(ExamType.MOCK);
        exam.setBatch("A");
        exam.setStandard("12th");
        exam.setExamDate(LocalDate.now());
        exam.setStartTime(LocalTime.of(10, 0));
        exam.setEndTime(LocalTime.of(11, 0));
        exam.setTotalMarks(100.0);
        examRepository.saveAndFlush(exam);

        Fee fee = new Fee();
        fee.setStudent(student);
        fee.setTotalAmount(1000.0);
        fee.setPaidAmount(400.0);
        fee.setRemainingAmount(600.0);
        feeRepository.saveAndFlush(fee);

        Marks marks = new Marks();
        marks.setStudent(student);
        marks.setExam(exam);
        marks.setObtainedMarks(88.0);
        marksRepository.saveAndFlush(marks);

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setDate(LocalDate.now());
        attendance.setPresent(true);
        attendanceRepository.saveAndFlush(attendance);

        Lecture lecture = new Lecture();
        lecture.setTeacher(teacher);
        lecture.setSubject("Physics");
        lecture.setBatch("A");
        lecture.setDate(LocalDate.now());
        lecture.setStartTime(LocalTime.of(12, 0));
        lecture.setEndTime(LocalTime.of(13, 0));
        lectureRepository.saveAndFlush(lecture);

        assertThat(userRepository.findByUsername("student-one")).contains(user);
        assertThat(studentRepository.findByUserUsername("student-one")).isEqualTo(student);
        assertThat(feeRepository.findByStudentId(student.getId())).hasSize(1);
        assertThat(marksRepository.findByStudentId(student.getId())).hasSize(1);
        assertThat(attendanceRepository.findByStudentId(student.getId())).hasSize(1);
        assertThat(lectureRepository.findById(lecture.getLectureId())).contains(lecture);
    }

    @Test
    void jwtRolesDriveMethodSecurityAnnotations() throws Exception {
        User admin = createUser("admin-one", Role.ADMIN);
        User studentUser = createUser("student-two", Role.STUDENT);
        User teacherUser = createUser("teacher-two", Role.TEACHER);

        Student student = new Student();
        student.setName("Student Two");
        student.setRollNo(studentUser.getUsername());
        student.setBranchId(1L);
        student.setUser(studentUser);
        studentRepository.saveAndFlush(student);

        Staff teacher = new Staff();
        teacher.setName(teacherUser.getUsername());
        teacher.setRole(StaffRole.TEACHER);
        staffRepository.saveAndFlush(teacher);

        mockMvc.perform(get("/students").header("Authorization", bearer(admin)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/students").header("Authorization", bearer(studentUser)))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/students/me").header("Authorization", bearer(studentUser)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/staff/me").header("Authorization", bearer(teacherUser)))
                .andExpect(status().isOk());
    }

    private User createUser(String username, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("1234");
        user.setRole(role);
        user.setBranchId(1L);
        return userRepository.saveAndFlush(user);
    }

    private String bearer(User user) {
        return "Bearer " + JwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }
}
