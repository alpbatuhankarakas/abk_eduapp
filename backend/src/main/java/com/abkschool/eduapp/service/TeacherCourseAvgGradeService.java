package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.model.TeacherCourseAvgGrade;
import com.abkschool.eduapp.repository.TeacherCourseAvgGradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCourseAvgGradeService {
    private final TeacherCourseAvgGradeRepository repo;

    public TeacherCourseAvgGradeService(TeacherCourseAvgGradeRepository repo) {
        this.repo = repo;
    }

    public List<TeacherCourseAvgGrade> getAll() {
        return repo.findAll();
    }

    public List<TeacherCourseAvgGrade> getByTeacherId(Long teacherId) {
        return repo.findByTeacherId(teacherId);
    }
}
