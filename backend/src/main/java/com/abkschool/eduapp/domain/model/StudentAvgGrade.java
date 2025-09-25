package com.abkschool.eduapp.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_avg_grade") // view
public class StudentAvgGrade {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    private String firstName;
    private String lastName;

    @Column(name = "avg_grade")
    private Double avgGrade;

    public Long getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Double getAvgGrade() { return avgGrade; }
}
