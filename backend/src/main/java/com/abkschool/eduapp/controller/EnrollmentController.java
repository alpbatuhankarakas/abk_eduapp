package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentCreateRequest;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentDTO;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentUpdateRequest;
import com.abkschool.eduapp.service.EnrollmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAll() {
        logger.info("GET /api/enrollments called");
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getById(@PathVariable Long id) {
        logger.info("GET /api/enrollments/{} called", id);
        EnrollmentDTO enrollment = enrollmentService.getById(id);
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> create(@Valid @RequestBody EnrollmentCreateRequest request) {
        logger.info("POST /api/enrollments called for studentId={} courseId={}", request.getStudentId(), request.getCourseId());
        EnrollmentDTO created = enrollmentService.createEnrollment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> update(@PathVariable Long id,
                                                @Valid @RequestBody EnrollmentUpdateRequest request) {
        logger.info("PUT /api/enrollments/{} called", id);
        EnrollmentDTO updated = enrollmentService.updateEnrollment(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("DELETE /api/enrollments/{} called", id);
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
