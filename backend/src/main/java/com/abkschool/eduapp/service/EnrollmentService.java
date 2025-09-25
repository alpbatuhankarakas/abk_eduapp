package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentCreateRequest;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentDTO;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentUpdateRequest;
import com.abkschool.eduapp.domain.mapper.EnrollmentMapper;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.domain.model.Enrollment;
import com.abkschool.eduapp.domain.model.Student;
import com.abkschool.eduapp.repository.CourseRepository;
import com.abkschool.eduapp.repository.EnrollmentRepository;
import com.abkschool.eduapp.repository.StudentRepository;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository,
                             EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    public List<EnrollmentDTO> getAllEnrollments() {
        logger.info("Fetching all enrollments");
        return enrollmentRepository.findAll()
                .stream()
                .map(enrollmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnrollmentDTO getById(Long id) {
        logger.info("Fetching enrollment with id={}", id);
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Enrollment with id={} not found", id);
                    return new RuntimeException("Enrollment not found");
                });
        return enrollmentMapper.toDTO(e);
    }

    public EnrollmentDTO createEnrollment(EnrollmentCreateRequest request) {
        logger.info("Creating enrollment studentId={} courseId={}", request.getStudentId(), request.getCourseId());

        if (enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            throw new DuplicateResourceException("Student already enrolled in this course");
        }

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Enrollment> existingEnrollments = enrollmentRepository.findByStudentId(request.getStudentId());
        for (Enrollment e : existingEnrollments) {
            Course existingCourse = e.getCourse();
            if (existingCourse.getStartDate() != null && existingCourse.getEndDate() != null &&
                    course.getStartDate() != null && course.getEndDate() != null) {

                boolean overlaps =
                        (course.getStartDate().isBefore(existingCourse.getEndDate()) &&
                                course.getEndDate().isAfter(existingCourse.getStartDate()));

                if (overlaps) {
                    throw new IllegalStateException("Student already enrolled in another course that overlaps in time");
                }
            }
        }

        Enrollment enrollment = enrollmentMapper.toEntity(request, student, course);
        Enrollment saved = enrollmentRepository.save(enrollment);

        logger.info("Created enrollment id={} studentId={} courseId={}", saved.getId(), student.getId(), course.getId());
        return enrollmentMapper.toDTO(saved);
    }

    public EnrollmentDTO updateEnrollment(Long id, EnrollmentUpdateRequest request) {
        logger.info("Updating enrollment with id={}", id);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollmentMapper.updateEntity(enrollment, request);
        Enrollment updated = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDTO(updated);
    }

    public void deleteEnrollment(Long id) {
        logger.info("Deleting enrollment with id={}", id);
        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found");
        }
        enrollmentRepository.deleteById(id);
    }
}
