package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.model.CourseAvgGrade;
import com.abkschool.eduapp.service.CourseAvgGradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseAvgGradeController {

    private final CourseAvgGradeService courseAvgGradeService;

    public CourseAvgGradeController(CourseAvgGradeService courseAvgGradeService) {
        this.courseAvgGradeService = courseAvgGradeService;
    }

    @GetMapping("/avg-grades")
    public List<CourseAvgGrade> getAllCourseAvgGrades() {
        return courseAvgGradeService.getAllCourseAvgGrades();
    }
}
