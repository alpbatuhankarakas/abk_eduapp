package com.abkschool.eduapp.domain.dto.enrollment;

public class EnrollmentUpdateRequest {
    private Double grade;
    private String status;

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
