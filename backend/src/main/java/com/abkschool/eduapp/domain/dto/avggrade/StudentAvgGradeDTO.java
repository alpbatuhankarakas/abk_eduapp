package com.abkschool.eduapp.domain.dto.avggrade;

public class StudentAvgGradeDTO {
    private Long studentId;
    private String firstName;
    private String lastName;
    private Double avgGrade;

    public StudentAvgGradeDTO(Long studentId, String firstName, String lastName, Double avgGrade) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avgGrade = avgGrade;
    }

    public Long getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Double getAvgGrade() { return avgGrade; }
}
