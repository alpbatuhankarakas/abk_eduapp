package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.model.TeacherCourseAvgGrade;
import com.abkschool.eduapp.service.TeacherCourseAvgGradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers/course-avg-grades")
public class TeacherCourseAvgGradeController {

    private final TeacherCourseAvgGradeService service;

    public TeacherCourseAvgGradeController(TeacherCourseAvgGradeService service) {
        this.service = service;
    }

    @GetMapping
    public List<TeacherCourseAvgGrade> getAll() {
        return service.getAll();
    }

    @GetMapping("/{teacherId}")
    public List<TeacherCourseAvgGrade> getByTeacherId(@PathVariable Long teacherId) {
        return service.getByTeacherId(teacherId);
    }
}
