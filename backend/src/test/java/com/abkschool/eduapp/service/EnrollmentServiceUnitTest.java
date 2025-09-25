package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentCreateRequest;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentDTO;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentUpdateRequest;
import com.abkschool.eduapp.domain.mapper.EnrollmentMapper;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.domain.model.Enrollment;
import com.abkschool.eduapp.domain.model.Student;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import com.abkschool.eduapp.repository.CourseRepository;
import com.abkschool.eduapp.repository.EnrollmentRepository;
import com.abkschool.eduapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceUnitTest {

    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private EnrollmentMapper enrollmentMapper;
    private EnrollmentService enrollmentService;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        enrollmentRepository = mock(EnrollmentRepository.class);
        studentRepository = mock(StudentRepository.class);
        courseRepository = mock(CourseRepository.class);
        enrollmentMapper = new EnrollmentMapper(); // gerçek mapper
        enrollmentService = new EnrollmentService(
                enrollmentRepository,
                studentRepository,
                courseRepository,
                enrollmentMapper
        );

        student = new Student();
        student.setId(42L);
        student.setFirstName("John");
        student.setLastName("Doe");

        course = new Course();
        course.setId(100L);
        course.setName("Math");
    }

    @Test
    void testCreateEnrollment_Success() {
        EnrollmentCreateRequest request = new EnrollmentCreateRequest();
        request.setStudentId(42L);
        request.setCourseId(100L);

        when(enrollmentRepository.existsByStudentIdAndCourseId(42L, 100L)).thenReturn(false);
        when(studentRepository.findById(42L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(100L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(inv -> {
            Enrollment e = inv.getArgument(0);
            e.setId(1L);
            return e;
        });

        EnrollmentDTO dto = enrollmentService.createEnrollment(request);

        assertNotNull(dto.getId());
        assertEquals(42L, dto.getStudentId());
        assertEquals(100L, dto.getCourseId());
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void testCreateEnrollment_Duplicate() {
        EnrollmentCreateRequest request = new EnrollmentCreateRequest();
        request.setStudentId(42L);
        request.setCourseId(100L);

        when(enrollmentRepository.existsByStudentIdAndCourseId(42L, 100L)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> enrollmentService.createEnrollment(request));
    }

    @Test
    void testGetById_NotFound() {
        when(enrollmentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> enrollmentService.getById(99L));

        assertEquals("Enrollment not found", ex.getMessage());
    }

    @Test
    void testUpdateEnrollment_Success() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setGrade(3.0);
        enrollment.setStatus("INACTIVE");

        EnrollmentUpdateRequest request = new EnrollmentUpdateRequest();
        request.setGrade(4.0);
        request.setStatus("ACTIVE");

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(inv -> inv.getArgument(0));

        EnrollmentDTO dto = enrollmentService.updateEnrollment(1L, request);

        assertEquals(4, dto.getGrade());
        assertEquals("ACTIVE", dto.getStatus());
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void testDeleteEnrollment_NotFound() {
        when(enrollmentRepository.existsById(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> enrollmentService.deleteEnrollment(99L));

        assertEquals("Enrollment not found", ex.getMessage());
    }

    @Test
    void testGetAllEnrollments() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        List<EnrollmentDTO> result = enrollmentService.getAllEnrollments();

        assertEquals(1, result.size());
        assertEquals(42L, result.get(0).getStudentId());
        assertEquals(100L, result.get(0).getCourseId());
    }
}
