package com.abkschool.eduapp.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_avg_grade") // view
public class CourseAvgGrade {

    @Id
    @Column(name = "course_id")
    private Long courseId;

    private String courseCode;
    private String courseName;
    private Integer credit;
    private String department;
    private Double avgGrade;
    private Long failureCount;

    // Getters
    public Long getCourseId() { return courseId; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public Integer getCredit() { return credit; }
    public String getDepartment() { return department; }
    public Double getAvgGrade() { return avgGrade; }
    public Long getFailureCount() { return failureCount; }
}
