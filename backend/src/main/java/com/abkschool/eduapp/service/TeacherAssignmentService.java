package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentDTO;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentCreateRequest;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentUpdateRequest;
import com.abkschool.eduapp.domain.mapper.TeacherAssignmentMapper;
import com.abkschool.eduapp.domain.model.Teacher;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.domain.model.TeacherAssignment;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import com.abkschool.eduapp.repository.TeacherAssignmentRepository;
import com.abkschool.eduapp.repository.TeacherRepository;
import com.abkschool.eduapp.repository.CourseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherAssignmentService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherAssignmentService.class);

    private final TeacherAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final TeacherAssignmentMapper mapper;

    public TeacherAssignmentService(TeacherAssignmentRepository assignmentRepository,
                                    TeacherRepository teacherRepository,
                                    CourseRepository courseRepository,
                                    TeacherAssignmentMapper mapper) {
        this.assignmentRepository = assignmentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    public List<TeacherAssignmentDTO> getAllAssignments() {
        logger.info("Fetching all teacher assignments");
        return assignmentRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public TeacherAssignmentDTO getAssignmentById(Long id) {
        logger.info("Fetching assignment with id={}", id);
        TeacherAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Assignment with id={} not found", id);
                    return new RuntimeException("Assignment not found");
                });
        return mapper.toDTO(assignment);
    }

    public TeacherAssignmentDTO createAssignment(TeacherAssignmentCreateRequest request) {
        logger.info("Creating new assignment teacherId={}, courseId={}", request.getTeacherId(), request.getCourseId());

        if (assignmentRepository.existsByTeacherIdAndCourseId(request.getTeacherId(), request.getCourseId())) {
            throw new DuplicateResourceException("Teacher already assigned to this course");
        }

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        TeacherAssignment assignment = mapper.toEntity(request, teacher, course);
        TeacherAssignment saved = assignmentRepository.save(assignment);

        logger.info("Created assignment id={} for teacherId={}, courseId={}", saved.getId(), teacher.getId(), course.getId());
        return mapper.toDTO(saved);
    }

    public TeacherAssignmentDTO updateAssignment(Long id, TeacherAssignmentUpdateRequest request) {
        logger.info("Updating assignment with id={}", id);
        TeacherAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        mapper.updateEntity(assignment, request);
        TeacherAssignment updated = assignmentRepository.save(assignment);

        logger.info("Updated assignment id={}", updated.getId());
        return mapper.toDTO(updated);
    }

    public void deleteAssignment(Long id) {
        logger.info("Deleting assignment with id={}", id);
        if (!assignmentRepository.existsById(id)) {
            logger.error("Assignment with id={} not found, cannot delete", id);
            throw new RuntimeException("Assignment not found");
        }
        assignmentRepository.deleteById(id);
        logger.info("Deleted assignment with id={}", id);
    }
}
