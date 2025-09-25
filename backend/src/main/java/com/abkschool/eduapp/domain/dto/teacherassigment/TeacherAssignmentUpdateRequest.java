package com.abkschool.eduapp.domain.dto.teacherassigment;

public class TeacherAssignmentUpdateRequest {

    private String role;   // (örn. INSTRUCTOR, ASSISTANT)
    private String status; // ACTIVE / INACTIVE

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
