package com.abkschool.eduapp.domain.dto.avggrade;

public class CourseAvgGradeDTO {
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Integer credit;
    private String department;
    private Double avgGrade;
    private Long failureCount;

    public CourseAvgGradeDTO(Long courseId, String courseCode, String courseName,
                             Integer credit, String department,
                             Double avgGrade, Long failureCount) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.department = department;
        this.avgGrade = avgGrade;
        this.failureCount = failureCount;
    }

    public Long getCourseId() { return courseId; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public Integer getCredit() { return credit; }
    public String getDepartment() { return department; }
    public Double getAvgGrade() { return avgGrade; }
    public Long getFailureCount() { return failureCount; }
}