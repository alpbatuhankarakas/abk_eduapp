package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.teacher.TeacherCreateRequest;
import com.abkschool.eduapp.domain.dto.teacher.TeacherDTO;
import com.abkschool.eduapp.domain.mapper.TeacherMapper;
import com.abkschool.eduapp.domain.model.Teacher;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import com.abkschool.eduapp.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceUnitTest {

    private TeacherRepository teacherRepository;
    private TeacherMapper teacherMapper;
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        teacherRepository = mock(TeacherRepository.class);
        teacherMapper = new TeacherMapper();
        teacherService = new TeacherService(teacherRepository, teacherMapper);
    }

    @Test
    void testCreateTeacher_Success() {
        TeacherCreateRequest request = new TeacherCreateRequest();
        request.setTeacherNumber("T9999");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");

        when(teacherRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(teacherRepository.existsByTeacherNumber(request.getTeacherNumber())).thenReturn(false);
        when(teacherRepository.save(any(Teacher.class))).thenAnswer(inv -> {
            Teacher t = inv.getArgument(0);
            t.setId(1L);
            return t;
        });

        TeacherDTO dto = teacherService.createTeacher(request);

        assertNotNull(dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("T9999", dto.getTeacherNumber());
    }

    @Test
    void testCreateTeacher_DuplicateEmail() {
        TeacherCreateRequest request = new TeacherCreateRequest();
        request.setEmail("john@example.com");
        request.setTeacherNumber("T8888");

        when(teacherRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> teacherService.createTeacher(request));
    }

    @Test
    void testGetById_NotFound() {
        when(teacherRepository.findById(42L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> teacherService.getById(42L));
        assertEquals("Teacher not found", ex.getMessage());
    }
}
