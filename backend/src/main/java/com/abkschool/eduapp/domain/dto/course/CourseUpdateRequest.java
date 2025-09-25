package com.abkschool.eduapp.domain.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CourseUpdateRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer credit;

    private String department;
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCredit() { return credit; }
    public void setCredit(Integer credit) { this.credit = credit; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
