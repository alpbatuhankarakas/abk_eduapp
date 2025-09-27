package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentCreateRequest;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentDTO;
import com.abkschool.eduapp.domain.mapper.EnrollmentMapper;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.domain.model.Student;
import com.abkschool.eduapp.repository.CourseRepository;
import com.abkschool.eduapp.repository.EnrollmentRepository;
import com.abkschool.eduapp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EnrollmentService.class, EnrollmentMapper.class})
@ActiveProfiles("test")
class EnrollmentServiceIntegrationTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateAndFindEnrollment() {
        // --- Arrange ---
        Student student = new Student();
        student.setStudentNumber("S11111");
        student.setFirstName("Bob");
        student.setLastName("Marley");
        student.setEmail("bob@example.com");
        entityManager.persist(student);

        Course course = new Course();
        course.setCourseCode("PHY101");
        course.setName("Physics");
        course.setCredit(4);
        course.setDepartment("Science");
        course.setStartDate(LocalDate.of(2025, 9, 1));   // ✅ eklendi
        course.setEndDate(LocalDate.of(2026, 6, 30));    // ✅ eklendi
        entityManager.persist(course);

        EnrollmentCreateRequest request = new EnrollmentCreateRequest();
        request.setStudentId(student.getId());
        request.setCourseId(course.getId());

        // --- Act ---
        EnrollmentDTO dto = enrollmentService.createEnrollment(request);

        // --- Assert ---
        assertNotNull(dto.getId());
        assertEquals(student.getId(), dto.getStudentId());
        assertEquals(course.getId(), dto.getCourseId());

        var found = enrollmentRepository.findById(dto.getId());
        assertTrue(found.isPresent());
        assertEquals(student.getId(), found.get().getStudent().getId());
        assertEquals(course.getId(), found.get().getCourse().getId());
    }

}
