package com.abkschool.eduapp.domain.dto.avggrade;

public class TeacherCourseAvgGradeDTO {
    private Long teacherId;
    private String teacherFirstName;
    private String teacherLastName;
    private Long courseId;
    private String courseName;
    private Double avgGrade;

    public TeacherCourseAvgGradeDTO(Long teacherId, String teacherFirstName, String teacherLastName,
                                    Long courseId, String courseName, Double avgGrade) {
        this.teacherId = teacherId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.avgGrade = avgGrade;
    }

    public Long getTeacherId() { return teacherId; }
    public String getTeacherFirstName() { return teacherFirstName; }
    public String getTeacherLastName() { return teacherLastName; }
    public Long getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public Double getAvgGrade() { return avgGrade; }
}
