package com.abkschool.eduapp.repository;

import com.abkschool.eduapp.domain.model.StudentAvgGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAvgGradeRepository extends JpaRepository<StudentAvgGrade, Long> {
}
