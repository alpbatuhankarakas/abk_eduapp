package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.model.CourseAvgGrade;
import com.abkschool.eduapp.repository.CourseAvgGradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseAvgGradeService {

    private final CourseAvgGradeRepository courseAvgGradeRepository;

    public CourseAvgGradeService(CourseAvgGradeRepository courseAvgGradeRepository) {
        this.courseAvgGradeRepository = courseAvgGradeRepository;
    }

    public List<CourseAvgGrade> getAllCourseAvgGrades() {
        return (List<CourseAvgGrade>) courseAvgGradeRepository.findAll();
    }
}
