package com.abkschool.eduapp.domain.mapper;

import com.abkschool.eduapp.domain.dto.course.*;
import com.abkschool.eduapp.domain.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) return null;
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setName(course.getName());
        dto.setCredit(course.getCredit());
        dto.setDepartment(course.getDepartment());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        return dto;
    }

    public Course toEntity(CourseCreateRequest request) {
        Course course = new Course();
        course.setCourseCode(request.getCourseCode());
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setDepartment(request.getDepartment());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());
        return course;
    }

    public void updateEntity(Course course, CourseUpdateRequest request) {
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setDepartment(request.getDepartment());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());
    }
}
