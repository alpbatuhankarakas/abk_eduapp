package com.abkschool.eduapp.repository;

import com.abkschool.eduapp.domain.model.CourseAvgGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseAvgGradeRepository extends JpaRepository<CourseAvgGrade, Long> {
}
