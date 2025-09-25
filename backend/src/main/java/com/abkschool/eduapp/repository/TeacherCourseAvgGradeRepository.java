package com.abkschool.eduapp.repository;

import com.abkschool.eduapp.domain.model.TeacherCourseAvgGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherCourseAvgGradeRepository extends JpaRepository<TeacherCourseAvgGrade, Long> {
    List<TeacherCourseAvgGrade> findByTeacherId(Long teacherId);
}
