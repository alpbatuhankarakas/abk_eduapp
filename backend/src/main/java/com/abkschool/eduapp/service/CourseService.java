package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.course.CourseDTO;
import com.abkschool.eduapp.domain.dto.course.CourseCreateRequest;
import com.abkschool.eduapp.domain.dto.course.CourseUpdateRequest;
import com.abkschool.eduapp.domain.mapper.CourseMapper;
import com.abkschool.eduapp.domain.model.Course;
import com.abkschool.eduapp.exception.DuplicateResourceException;
import com.abkschool.eduapp.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> getAllCourses() {
        logger.info("Fetching all courses");
        List<CourseDTO> courses = courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
        logger.debug("Fetched {} courses", courses.size());
        return courses;
    }

    public CourseDTO getById(Long id) {
        logger.info("Fetching course with id={}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Course with id={} not found", id);
                    return new RuntimeException("Course not found");
                });
        return courseMapper.toDTO(course);
    }

    public CourseDTO createCourse(CourseCreateRequest request) {
        logger.info("Creating course with code={}", request.getCourseCode());

        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new DuplicateResourceException("Course code already exists: " + request.getCourseCode());
        }

        Course course = courseMapper.toEntity(request);
        Course saved = courseRepository.save(course);

        logger.info("Created course with id={}", saved.getId());
        return courseMapper.toDTO(saved);
    }

    public CourseDTO updateCourse(Long id, CourseUpdateRequest request) {
        logger.info("Updating course with id={}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Course with id={} not found", id);
                    return new RuntimeException("Course not found");
                });

        courseMapper.updateEntity(course, request);
        Course updated = courseRepository.save(course);

        logger.info("Updated course with id={}", updated.getId());
        return courseMapper.toDTO(updated);
    }

    public void deleteCourse(Long id) {
        logger.info("Deleting course with id={}", id);

        if (!courseRepository.existsById(id)) {
            logger.error("Course with id={} not found, cannot delete", id);
            throw new RuntimeException("Course not found");
        }

        courseRepository.deleteById(id);
        logger.info("Deleted course with id={}", id);
    }
}
