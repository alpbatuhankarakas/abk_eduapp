package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.course.CourseDTO;
import com.abkschool.eduapp.domain.dto.course.CourseCreateRequest;
import com.abkschool.eduapp.domain.dto.course.CourseUpdateRequest;
import com.abkschool.eduapp.service.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAll() {
        logger.info("GET /api/courses called");
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable Long id) {
        logger.info("GET /api/courses/{} called", id);
        CourseDTO course = courseService.getById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@Valid @RequestBody CourseCreateRequest request) {
        logger.info("POST /api/courses called for code={}", request.getCourseCode());
        CourseDTO created = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody CourseUpdateRequest request) {
        logger.info("PUT /api/courses/{} called", id);
        CourseDTO updated = courseService.updateCourse(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("DELETE /api/courses/{} called", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
