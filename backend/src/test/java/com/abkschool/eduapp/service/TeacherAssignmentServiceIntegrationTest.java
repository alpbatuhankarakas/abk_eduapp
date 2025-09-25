package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentCreateRequest;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentDTO;
import com.abkschool.eduapp.domain.mapper.TeacherAssignmentMapper;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.domain.model.Teacher;
import com.abkschool.eduapp.repository.CourseRepository;
import com.abkschool.eduapp.repository.TeacherAssignmentRepository;
import com.abkschool.eduapp.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({TeacherAssignmentService.class, TeacherAssignmentMapper.class})
@ActiveProfiles("test")
class TeacherAssignmentServiceIntegrationTest {

    @Autowired
    private TeacherAssignmentService assignmentService;

    @Autowired
    private TeacherAssignmentRepository assignmentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateAndFindAssignment() {
        // --- Arrange ---
        Teacher teacher = new Teacher();
        teacher.setTeacherNumber("T1001");
        teacher.setFirstName("Alice");
        teacher.setLastName("Smith");
        teacher.setEmail("alice@example.com");
        entityManager.persist(teacher);

        Course course = new Course();
        course.setCourseCode("MATH101");
        course.setName("Mathematics");
        course.setCredit(5);
        course.setDepartment("Science");
        entityManager.persist(course);

        TeacherAssignmentCreateRequest request = new TeacherAssignmentCreateRequest();
        request.setTeacherId(teacher.getId());
        request.setCourseId(course.getId());
        request.setRole("INSTRUCTOR");

        // --- Act ---
        TeacherAssignmentDTO dto = assignmentService.createAssignment(request);

        // --- Assert ---
        assertNotNull(dto.getId());
        assertEquals(teacher.getId(), dto.getTeacherId());
        assertEquals(course.getId(), dto.getCourseId());
        assertEquals("INSTRUCTOR", dto.getRole());

        var found = assignmentRepository.findById(dto.getId());
        assertTrue(found.isPresent());
        assertEquals(teacher.getId(), found.get().getTeacher().getId());
        assertEquals(course.getId(), found.get().getCourse().getId());
    }
}
