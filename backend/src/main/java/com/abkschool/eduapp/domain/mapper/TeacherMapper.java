package com.abkschool.eduapp.domain.mapper;

import com.abkschool.eduapp.domain.dto.teacher.TeacherDTO;
import com.abkschool.eduapp.domain.dto.teacher.TeacherCreateRequest;
import com.abkschool.eduapp.domain.dto.teacher.TeacherUpdateRequest;
import com.abkschool.eduapp.domain.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public TeacherDTO toDTO(Teacher teacher) {
        if (teacher == null) return null;

        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setTeacherNumber(teacher.getTeacherNumber());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setEmail(teacher.getEmail());
        dto.setPhone(teacher.getPhone());
        dto.setDepartment(teacher.getDepartment());
        dto.setTitle(teacher.getTitle());
        dto.setStatus(teacher.getStatus());
        return dto;
    }

    public Teacher toEntity(TeacherCreateRequest request) {
        if (request == null) return null;

        Teacher teacher = new Teacher();
        teacher.setTeacherNumber(request.getTeacherNumber());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        teacher.setDepartment(request.getDepartment());
        teacher.setTitle(request.getTitle());
        return teacher;
    }

    public void updateEntity(Teacher teacher, TeacherUpdateRequest request) {
        if (teacher == null || request == null) return;

        if (request.getFirstName() != null) teacher.setFirstName(request.getFirstName());
        if (request.getLastName() != null) teacher.setLastName(request.getLastName());
        if (request.getEmail() != null) teacher.setEmail(request.getEmail());
        if (request.getPhone() != null) teacher.setPhone(request.getPhone());
        if (request.getDepartment() != null) teacher.setDepartment(request.getDepartment());
        if (request.getTitle() != null) teacher.setTitle(request.getTitle());
        if (request.getStatus() != null) teacher.setStatus(request.getStatus());
    }
}
