package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.course.CourseCreateRequest;
import com.abkschool.eduapp.domain.dto.course.CourseDTO;
import com.abkschool.eduapp.domain.mapper.CourseMapper;
import com.abkschool.eduapp.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({CourseService.class, CourseMapper.class})
@ActiveProfiles("test")
class CourseServiceIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateAndFindCourse() {
        CourseCreateRequest request = new CourseCreateRequest();
        request.setCourseCode("CS500");
        request.setName("Operating Systems");
        request.setCredit(4);
        request.setStartDate(LocalDate.of(2025, 9, 1));
        request.setEndDate(LocalDate.of(2026, 6, 30));

        CourseDTO dto = courseService.createCourse(request);

        assertNotNull(dto.getId());
        assertEquals("CS500", dto.getCourseCode());
        assertEquals("Operating Systems", dto.getName());

        var found = courseRepository.findById(dto.getId());
        assertTrue(found.isPresent());
    }
}
