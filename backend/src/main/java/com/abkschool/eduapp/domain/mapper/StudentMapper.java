package com.abkschool.eduapp.domain.mapper;

import com.abkschool.eduapp.domain.dto.student.StudentDTO;
import com.abkschool.eduapp.domain.dto.student.StudentCreateRequest;
import com.abkschool.eduapp.domain.dto.student.StudentUpdateRequest;
import com.abkschool.eduapp.domain.model.Student;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        if (student == null) return null;

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setStudentNumber(student.getStudentNumber());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setGender(student.getGender());
        dto.setPhone(student.getPhone());
        dto.setAddress(student.getAddress());
        dto.setEnrollmentYear(student.getEnrollmentYear());
        dto.setDepartment(student.getDepartment());
        dto.setStatus(student.getStatus());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());

        return dto;
    }

    public Student toEntity(StudentCreateRequest request) {
        if (request == null) return null;

        Student student = new Student();
        student.setStudentNumber(request.getStudentNumber());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());
        student.setEnrollmentYear(request.getEnrollmentYear());
        student.setDepartment(request.getDepartment());
        student.setStatus(request.getStatus());

        return student;
    }

    public void updateEntity(Student student, StudentUpdateRequest request) {
        if (student == null || request == null) return;

        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getEmail() != null) student.setEmail(request.getEmail());
        if (request.getDateOfBirth() != null) student.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) student.setGender(request.getGender());
        if (request.getPhone() != null) student.setPhone(request.getPhone());
        if (request.getAddress() != null) student.setAddress(request.getAddress());
        if (request.getDepartment() != null) student.setDepartment(request.getDepartment());
        if (request.getStatus() != null) student.setStatus(request.getStatus());
    }
}
