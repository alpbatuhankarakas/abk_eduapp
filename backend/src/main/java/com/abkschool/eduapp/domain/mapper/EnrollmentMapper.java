package com.abkschool.eduapp.domain.mapper;

import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentDTO;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentCreateRequest;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentUpdateRequest;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.domain.model.Enrollment;
import com.abkschool.eduapp.domain.model.Student;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentDTO toDTO(Enrollment e) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(e.getId());
        dto.setStudentId(e.getStudent().getId());
        dto.setCourseId(e.getCourse().getId());
        dto.setGrade(e.getGrade());
        dto.setStatus(e.getStatus());
        dto.setStartDate(e.getCourse().getStartDate());
        dto.setEndDate(e.getCourse().getEndDate());
        return dto;
    }

    public Enrollment toEntity(EnrollmentCreateRequest request, Student student, Course course) {
        Enrollment e = new Enrollment();
        e.setStudent(student);
        e.setCourse(course);
        return e;
    }

    public void updateEntity(Enrollment e, EnrollmentUpdateRequest request) {
        if (request.getGrade() != null) e.setGrade(request.getGrade());
        if (request.getStatus() != null) e.setStatus(request.getStatus());
    }
}
