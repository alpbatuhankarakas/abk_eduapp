package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.teacher.TeacherCreateRequest;
import com.abkschool.eduapp.domain.dto.teacher.TeacherDTO;
import com.abkschool.eduapp.domain.mapper.TeacherMapper;
import com.abkschool.eduapp.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({TeacherService.class, TeacherMapper.class})
@ActiveProfiles("test")
class TeacherServiceIntegrationTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateAndFindTeacher() {
        TeacherCreateRequest request = new TeacherCreateRequest();
        request.setTeacherNumber("T12345");
        request.setFirstName("Jane");
        request.setLastName("Doe");
        request.setEmail("jane@example.com");

        TeacherDTO dto = teacherService.createTeacher(request);

        assertNotNull(dto.getId());
        assertEquals("Jane", dto.getFirstName());
        assertTrue(teacherRepository.findById(dto.getId()).isPresent());
    }
}
