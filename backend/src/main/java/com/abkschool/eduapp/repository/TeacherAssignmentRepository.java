package com.abkschool.eduapp.repository;

import com.abkschool.eduapp.domain.model.TeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long> {

    Optional<TeacherAssignment> findByTeacherIdAndCourseId(Long teacherId, Long courseId);

    List<TeacherAssignment> findByTeacherId(Long teacherId);

    List<TeacherAssignment> findByCourseId(Long courseId);

    boolean existsByTeacherIdAndCourseId(Long teacherId, Long courseId);
}
