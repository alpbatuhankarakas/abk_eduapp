package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.teacher.TeacherDTO;
import com.abkschool.eduapp.domain.dto.teacher.TeacherCreateRequest;
import com.abkschool.eduapp.domain.dto.teacher.TeacherUpdateRequest;
import com.abkschool.eduapp.service.TeacherService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAll() {
        logger.info("GET /api/teachers called");
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable Long id) {
        logger.info("GET /api/teachers/{} called", id);
        TeacherDTO teacher = teacherService.getById(id);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> create(@Valid @RequestBody TeacherCreateRequest request) {
        logger.info("POST /api/teachers called for teacherNumber={}", request.getTeacherNumber());
        TeacherDTO created = teacherService.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody TeacherUpdateRequest request) {
        logger.info("PUT /api/teachers/{} called", id);
        TeacherDTO updated = teacherService.updateTeacher(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("DELETE /api/teachers/{} called", id);
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
