package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.student.StudentDTO;
import com.abkschool.eduapp.domain.dto.student.StudentCreateRequest;
import com.abkschool.eduapp.domain.dto.student.StudentUpdateRequest;
import com.abkschool.eduapp.domain.mapper.StudentMapper;
import com.abkschool.eduapp.domain.model.Student;
import com.abkschool.eduapp.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.abkschool.eduapp.exception.DuplicateResourceException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public List<StudentDTO> getAllStudents() {
        logger.info("Fetching all students");
        List<StudentDTO> students = studentRepository.findAll()
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
        logger.debug("Fetched {} students", students.size());
        return students;
    }

    public StudentDTO getById(Long id) {
        logger.info("Fetching student with id={}", id);
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student with id={} not found", id);
                    return new RuntimeException("Student not found");
                });
        return studentMapper.toDTO(s);
    }

    public StudentDTO createStudent(StudentCreateRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }
        if (studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new DuplicateResourceException("Student number already exists: " + request.getStudentNumber());
        }

        Student student = studentMapper.toEntity(request);
        Student saved = studentRepository.save(student);
        return studentMapper.toDTO(saved);
    }
    public StudentDTO updateStudent(Long id, StudentUpdateRequest request) {
        logger.info("Updating student with id={}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student with id={} not found", id);
                    return new RuntimeException("Student not found");
                });

        studentMapper.updateEntity(student, request);
        Student updated = studentRepository.save(student);
        logger.info("Updated student with id={}", updated.getId());
        return studentMapper.toDTO(updated);
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with id={}", id);
        if (!studentRepository.existsById(id)) {
            logger.error("Student with id={} not found, cannot delete", id);
            throw new RuntimeException("Student not found");
        }
        studentRepository.deleteById(id);
        logger.info("Deleted student with id={}", id);
    }
}
