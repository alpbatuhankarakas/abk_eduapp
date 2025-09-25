package com.abkschool.eduapp.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_course_avg_grade")
public class TeacherCourseAvgGrade {

    @Id
    @Column(name = "teacher_id")
    private Long teacherId;

    private String firstName;
    private String lastName;

    @Column(name = "course_id")
    private Long courseId;

    private String courseName;

    @Column(name = "avg_grade")
    private Double avgGrade;

    // Getters
    public Long getTeacherId() { return teacherId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Long getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public Double getAvgGrade() { return avgGrade; }
}
