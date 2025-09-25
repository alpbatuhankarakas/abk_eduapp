package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.student.StudentCreateRequest;
import com.abkschool.eduapp.domain.dto.student.StudentDTO;
import com.abkschool.eduapp.domain.mapper.StudentMapper;
import com.abkschool.eduapp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({StudentService.class, StudentMapper.class})
@ActiveProfiles("test")
class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateAndFindStudent() {
        StudentCreateRequest request = new StudentCreateRequest();
        request.setStudentNumber("S99999");
        request.setFirstName("Alice");
        request.setLastName("Smith");
        request.setEmail("alice@example.com");

        StudentDTO dto = studentService.createStudent(request);

        assertNotNull(dto.getId());
        assertEquals("Alice", dto.getFirstName());

        var found = studentRepository.findById(dto.getId());
        assertTrue(found.isPresent());
    }
}
