package com.abkschool.eduapp.service;

import com.abkschool.eduapp.domain.dto.teacher.TeacherDTO;
import com.abkschool.eduapp.domain.dto.teacher.TeacherCreateRequest;
import com.abkschool.eduapp.domain.dto.teacher.TeacherUpdateRequest;
import com.abkschool.eduapp.domain.mapper.TeacherMapper;
import com.abkschool.eduapp.domain.model.Teacher;
import com.abkschool.eduapp.repository.TeacherRepository;
import com.abkschool.eduapp.exception.DuplicateResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public List<TeacherDTO> getAllTeachers() {
        logger.info("Fetching all teachers");
        List<TeacherDTO> teachers = teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
        logger.debug("Fetched {} teachers", teachers.size());
        return teachers;
    }

    public TeacherDTO getById(Long id) {
        logger.info("Fetching teacher with id={}", id);
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Teacher with id={} not found", id);
                    return new RuntimeException("Teacher not found");
                });
        return teacherMapper.toDTO(teacher);
    }

    public TeacherDTO createTeacher(TeacherCreateRequest request) {
        if (teacherRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }
        if (teacherRepository.existsByTeacherNumber(request.getTeacherNumber())) {
            throw new DuplicateResourceException("Teacher number already exists: " + request.getTeacherNumber());
        }

        Teacher teacher = teacherMapper.toEntity(request);
        Teacher saved = teacherRepository.save(teacher);
        return teacherMapper.toDTO(saved);
    }

    public TeacherDTO updateTeacher(Long id, TeacherUpdateRequest request) {
        logger.info("Updating teacher with id={}", id);
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Teacher with id={} not found", id);
                    return new RuntimeException("Teacher not found");
                });

        teacherMapper.updateEntity(teacher, request);
        Teacher updated = teacherRepository.save(teacher);
        logger.info("Updated teacher with id={}", updated.getId());
        return teacherMapper.toDTO(updated);
    }

    public void deleteTeacher(Long id) {
        logger.info("Deleting teacher with id={}", id);
        if (!teacherRepository.existsById(id)) {
            logger.error("Teacher with id={} not found, cannot delete", id);
            throw new RuntimeException("Teacher not found");
        }
        teacherRepository.deleteById(id);
        logger.info("Deleted teacher with id={}", id);
    }
}
