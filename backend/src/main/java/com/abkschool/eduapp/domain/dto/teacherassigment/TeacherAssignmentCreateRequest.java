package com.abkschool.eduapp.domain.dto.teacherassigment;

public class TeacherAssignmentCreateRequest {

    private Long teacherId;
    private Long courseId;
    private String role;

    public Long getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCourseId() {
        return courseId;
    }
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
