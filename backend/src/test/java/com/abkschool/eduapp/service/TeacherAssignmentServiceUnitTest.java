package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentCreateRequest;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentDTO;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentUpdateRequest;
import com.abkschool.eduapp.domain.mapper.TeacherAssignmentMapper;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.domain.model.Teacher;
import com.abkschool.eduapp.domain.model.TeacherAssignment;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import com.abkschool.eduapp.repository.CourseRepository;
import com.abkschool.eduapp.repository.TeacherAssignmentRepository;
import com.abkschool.eduapp.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherAssignmentServiceUnitTest {

    private TeacherAssignmentRepository assignmentRepository;
    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;
    private TeacherAssignmentMapper mapper;
    private TeacherAssignmentService assignmentService;

    private Teacher teacher;
    private Course course;

    @BeforeEach
    void setUp() {
        assignmentRepository = mock(TeacherAssignmentRepository.class);
        teacherRepository = mock(TeacherRepository.class);
        courseRepository = mock(CourseRepository.class);
        mapper = new TeacherAssignmentMapper(); // gerçek mapper
        assignmentService = new TeacherAssignmentService(
                assignmentRepository, teacherRepository, courseRepository, mapper
        );

        teacher = new Teacher();
        teacher.setId(10L);
        teacher.setFirstName("Alice");

        course = new Course();
        course.setId(20L);
        course.setName("Physics");
    }

    @Test
    void testCreateAssignment_Success() {
        TeacherAssignmentCreateRequest request = new TeacherAssignmentCreateRequest();
        request.setTeacherId(10L);
        request.setCourseId(20L);
        request.setRole("INSTRUCTOR");

        when(assignmentRepository.existsByTeacherIdAndCourseId(10L, 20L)).thenReturn(false);
        when(teacherRepository.findById(10L)).thenReturn(Optional.of(teacher));
        when(courseRepository.findById(20L)).thenReturn(Optional.of(course));
        when(assignmentRepository.save(any(TeacherAssignment.class))).thenAnswer(inv -> {
            TeacherAssignment a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        TeacherAssignmentDTO dto = assignmentService.createAssignment(request);

        assertNotNull(dto.getId());
        assertEquals(10L, dto.getTeacherId());
        assertEquals(20L, dto.getCourseId());
        verify(assignmentRepository, times(1)).save(any(TeacherAssignment.class));
    }

    @Test
    void testCreateAssignment_Duplicate() {
        TeacherAssignmentCreateRequest request = new TeacherAssignmentCreateRequest();
        request.setTeacherId(10L);
        request.setCourseId(20L);

        when(assignmentRepository.existsByTeacherIdAndCourseId(10L, 20L)).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> assignmentService.createAssignment(request));
    }

    @Test
    void testGetById_NotFound() {
        when(assignmentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> assignmentService.getAssignmentById(99L));

        assertEquals("Assignment not found", ex.getMessage());
    }

    @Test
    void testUpdateAssignment_Success() {
        TeacherAssignment assignment = new TeacherAssignment();
        assignment.setId(1L);
        assignment.setTeacher(teacher);
        assignment.setCourse(course);
        assignment.setAssignedDate(LocalDateTime.now());
        assignment.setRole("INSTRUCTOR");

        TeacherAssignmentUpdateRequest request = new TeacherAssignmentUpdateRequest();
        request.setRole("ASSISTANT");

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(any(TeacherAssignment.class))).thenAnswer(inv -> inv.getArgument(0));

        TeacherAssignmentDTO dto = assignmentService.updateAssignment(1L, request);

        assertEquals("ASSISTANT", dto.getRole());
        verify(assignmentRepository, times(1)).save(any(TeacherAssignment.class));
    }

    @Test
    void testDeleteAssignment_NotFound() {
        when(assignmentRepository.existsById(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> assignmentService.deleteAssignment(99L));

        assertEquals("Assignment not found", ex.getMessage());
    }

    @Test
    void testGetAllAssignments() {
        TeacherAssignment assignment = new TeacherAssignment();
        assignment.setId(1L);
        assignment.setTeacher(teacher);
        assignment.setCourse(course);
        assignment.setAssignedDate(LocalDateTime.now());

        when(assignmentRepository.findAll()).thenReturn(List.of(assignment));

        List<TeacherAssignmentDTO> result = assignmentService.getAllAssignments();

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getTeacherId());
        assertEquals(20L, result.get(0).getCourseId());
    }
}
