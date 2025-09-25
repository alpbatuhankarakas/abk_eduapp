package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.model.StudentAvgGrade;
import com.abkschool.eduapp.repository.StudentAvgGradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAvgGradeService {

    private final StudentAvgGradeRepository avgGradeRepository;

    public StudentAvgGradeService(StudentAvgGradeRepository avgGradeRepository) {
        this.avgGradeRepository = avgGradeRepository;
    }

    public List<StudentAvgGrade> getAllAvgGrades() {
        return avgGradeRepository.findAll();
    }

    public StudentAvgGrade getAvgGradeByStudentId(Long studentId) {
        return avgGradeRepository.findById(studentId)
                .orElse(null);
    }
}
