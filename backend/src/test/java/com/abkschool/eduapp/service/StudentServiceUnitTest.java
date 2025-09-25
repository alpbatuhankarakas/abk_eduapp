package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.student.StudentCreateRequest;
import com.abkschool.eduapp.domain.dto.student.StudentDTO;
import com.abkschool.eduapp.domain.mapper.StudentMapper;
import com.abkschool.eduapp.domain.model.Student;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import com.abkschool.eduapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceUnitTest {

    private StudentRepository studentRepository;
    private StudentMapper studentMapper;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        studentMapper = new StudentMapper();
        studentService = new StudentService(studentRepository, studentMapper);
    }

    @Test
    void testCreateStudent_Success() {
        StudentCreateRequest request = new StudentCreateRequest();
        request.setStudentNumber("S99999");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");

        when(studentRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(studentRepository.existsByStudentNumber(request.getStudentNumber())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenAnswer(inv -> {
            Student s = inv.getArgument(0);
            s.setId(1L);
            return s;
        });

        StudentDTO dto = studentService.createStudent(request);

        assertNotNull(dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("S99999", dto.getStudentNumber());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testCreateStudent_DuplicateEmail() {
        StudentCreateRequest request = new StudentCreateRequest();
        request.setEmail("john@example.com");
        request.setStudentNumber("S99999");

        when(studentRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> studentService.createStudent(request));
    }

    @Test
    void testGetById_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> studentService.getById(99L));
        assertEquals("Student not found", ex.getMessage());
    }
}
