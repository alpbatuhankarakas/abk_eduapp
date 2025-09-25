package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.model.StudentAvgGrade;
import com.abkschool.eduapp.service.StudentAvgGradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/avg-grades")
public class StudentAvgGradeController {

    private final StudentAvgGradeService avgGradeService;

    public StudentAvgGradeController(StudentAvgGradeService avgGradeService) {
        this.avgGradeService = avgGradeService;
    }

    @GetMapping
    public ResponseEntity<List<StudentAvgGrade>> getAll() {
        return ResponseEntity.ok(avgGradeService.getAllAvgGrades());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentAvgGrade> getByStudent(@PathVariable Long studentId) {
        StudentAvgGrade result = avgGradeService.getAvgGradeByStudentId(studentId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
