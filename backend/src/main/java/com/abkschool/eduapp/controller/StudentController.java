package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.student.StudentDTO;
import com.abkschool.eduapp.domain.dto.student.StudentCreateRequest;
import com.abkschool.eduapp.domain.dto.student.StudentUpdateRequest;
import com.abkschool.eduapp.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAll() {
        logger.info("GET /api/students called");
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Long id) {
        logger.info("GET /api/students/{} called", id);
        StudentDTO student = studentService.getById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@Valid @RequestBody StudentCreateRequest request) {
        logger.info("POST /api/students called for studentNumber={}", request.getStudentNumber());
        StudentDTO created = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody StudentUpdateRequest request) {
        logger.info("PUT /api/students/{} called", id);
        StudentDTO updated = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("DELETE /api/students/{} called", id);
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }


}
