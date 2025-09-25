package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.course.CourseCreateRequest;
import com.abkschool.eduapp.domain.dto.course.CourseDTO;
import com.abkschool.eduapp.domain.mapper.CourseMapper;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import com.abkschool.eduapp.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceUnitTest {

    private CourseRepository courseRepository;
    private CourseMapper courseMapper;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        courseMapper = new CourseMapper();
        courseService = new CourseService(courseRepository, courseMapper);
    }

    @Test
    void testCreateCourse_Success() {
        CourseCreateRequest request = new CourseCreateRequest();
        request.setCourseCode("CS300");
        request.setName("Databases");
        request.setCredit(3);

        when(courseRepository.existsByCourseCode(request.getCourseCode())).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenAnswer(inv -> {
            Course c = inv.getArgument(0);
            c.setId(1L);
            return c;
        });

        CourseDTO dto = courseService.createCourse(request);

        assertNotNull(dto.getId());
        assertEquals("CS300", dto.getCourseCode());
        assertEquals("Databases", dto.getName());
    }

    @Test
    void testCreateCourse_DuplicateCode() {
        CourseCreateRequest request = new CourseCreateRequest();
        request.setCourseCode("CS400");
        request.setName("Networks");
        request.setCredit(4);

        when(courseRepository.existsByCourseCode("CS400")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> courseService.createCourse(request));
    }

    @Test
    void testGetById_NotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> courseService.getById(99L));
        assertEquals("Course not found", ex.getMessage());
    }
}
