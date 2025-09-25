package com.abkschool.eduapp.domain.mapper;

import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentDTO;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentCreateRequest;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentUpdateRequest;
import com.abkschool.eduapp.domain.model.TeacherAssignment;
import com.abkschool.eduapp.domain.model.Teacher;
import com.abkschool.eduapp.domain.model.Course;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TeacherAssignmentMapper {

    public TeacherAssignmentDTO toDTO(TeacherAssignment assignment) {
        if (assignment == null) return null;

        TeacherAssignmentDTO dto = new TeacherAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setTeacherId(assignment.getTeacher() != null ? assignment.getTeacher().getId() : null);
        dto.setCourseId(assignment.getCourse() != null ? assignment.getCourse().getId() : null);
        dto.setRole(assignment.getRole());
        dto.setAssignedDate(assignment.getAssignedDate());
        dto.setCreatedAt(assignment.getCreatedAt());
        dto.setUpdatedAt(assignment.getUpdatedAt());

        return dto;
    }

    public TeacherAssignment toEntity(TeacherAssignmentCreateRequest request, Teacher teacher, Course course) {
        if (request == null) return null;

        TeacherAssignment assignment = new TeacherAssignment();
        assignment.setTeacher(teacher);
        assignment.setCourse(course);
        assignment.setRole(request.getRole() != null ? request.getRole() : "INSTRUCTOR");
        assignment.setAssignedDate(LocalDateTime.now());

        return assignment;
    }

    public void updateEntity(TeacherAssignment assignment, TeacherAssignmentUpdateRequest request) {
        if (assignment == null || request == null) return;

        if (request.getRole() != null) {
            assignment.setRole(request.getRole());
        }
        // Status alanı DB’de yoksa, request’teki status’u kullanmana gerek olmayabilir.
        // Eğer tabloya ekleyeceksen buradan da set edebilirsin.
    }
}
