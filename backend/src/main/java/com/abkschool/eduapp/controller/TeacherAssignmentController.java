package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentDTO;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentCreateRequest;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentUpdateRequest;
import com.abkschool.eduapp.service.TeacherAssignmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class TeacherAssignmentController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherAssignmentController.class);

    private final TeacherAssignmentService assignmentService;

    public TeacherAssignmentController(TeacherAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherAssignmentDTO>> getAll() {
        logger.info("GET /api/assignments called");
        List<TeacherAssignmentDTO> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherAssignmentDTO> getById(@PathVariable Long id) {
        logger.info("GET /api/assignments/{} called", id);
        TeacherAssignmentDTO assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    @PostMapping
    public ResponseEntity<TeacherAssignmentDTO> create(@Valid @RequestBody TeacherAssignmentCreateRequest request) {
        logger.info("POST /api/assignments called for teacherId={}, courseId={}", request.getTeacherId(), request.getCourseId());
        TeacherAssignmentDTO created = assignmentService.createAssignment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherAssignmentDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody TeacherAssignmentUpdateRequest request) {
        logger.info("PUT /api/assignments/{} called", id);
        TeacherAssignmentDTO updated = assignmentService.updateAssignment(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("DELETE /api/assignments/{} called", id);
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
