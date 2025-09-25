package com.abkschool.eduapp.domain.dto.teacherassigment;

import java.time.LocalDateTime;

public class TeacherAssignmentDTO {
    private Long id;
    private Long teacherId;
    private Long courseId;
    private String role;
    private LocalDateTime assignedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TeacherAssignmentDTO() {}

    public TeacherAssignmentDTO(Long id, Long teacherId, Long courseId,
                                String role, LocalDateTime assignedDate,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.role = role;
        this.assignedDate = assignedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }
    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
