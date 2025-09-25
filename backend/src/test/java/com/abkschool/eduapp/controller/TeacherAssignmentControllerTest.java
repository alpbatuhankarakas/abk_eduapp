package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentCreateRequest;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentDTO;
import com.abkschool.eduapp.domain.dto.teacherassigment.TeacherAssignmentUpdateRequest;
import com.abkschool.eduapp.service.TeacherAssignmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherAssignmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {TeacherAssignmentController.class, TeacherAssignmentControllerTest.MockConfig.class})
class TeacherAssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherAssignmentService assignmentService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        TeacherAssignmentService assignmentService() {
            return Mockito.mock(TeacherAssignmentService.class);
        }
    }

    @Test
    void shouldReturnAllAssignments() throws Exception {
        TeacherAssignmentDTO dto = new TeacherAssignmentDTO(
                1L, 10L, 20L, "INSTRUCTOR",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()
        );

        Mockito.when(assignmentService.getAllAssignments()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/assignments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherId").value(10L))
                .andExpect(jsonPath("$[0].courseId").value(20L));
    }

    @Test
    void shouldReturnAssignmentById() throws Exception {
        TeacherAssignmentDTO dto = new TeacherAssignmentDTO(
                1L, 10L, 20L, "INSTRUCTOR",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()
        );

        Mockito.when(assignmentService.getAssignmentById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/assignments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacherId").value(10L))
                .andExpect(jsonPath("$.courseId").value(20L));
    }

    @Test
    void shouldCreateAssignment() throws Exception {
        TeacherAssignmentCreateRequest request = new TeacherAssignmentCreateRequest();
        request.setTeacherId(10L);
        request.setCourseId(20L);
        request.setRole("INSTRUCTOR");

        TeacherAssignmentDTO dto = new TeacherAssignmentDTO(
                1L, 10L, 20L, "INSTRUCTOR",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()
        );

        Mockito.when(assignmentService.createAssignment(any(TeacherAssignmentCreateRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"teacherId":10,"courseId":20,"role":"INSTRUCTOR"}
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.teacherId").value(10L))
                .andExpect(jsonPath("$.courseId").value(20L));
    }

    @Test
    void shouldUpdateAssignment() throws Exception {
        TeacherAssignmentUpdateRequest request = new TeacherAssignmentUpdateRequest();
        request.setRole("ASSISTANT");

        TeacherAssignmentDTO dto = new TeacherAssignmentDTO(
                1L, 10L, 20L, "ASSISTANT",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()
        );

        Mockito.when(assignmentService.updateAssignment(eq(1L), any(TeacherAssignmentUpdateRequest.class))).thenReturn(dto);

        mockMvc.perform(put("/api/assignments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"role":"ASSISTANT"}
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ASSISTANT"));
    }

    @Test
    void shouldDeleteAssignment() throws Exception {
        Mockito.doNothing().when(assignmentService).deleteAssignment(1L);

        mockMvc.perform(delete("/api/assignments/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(assignmentService, Mockito.times(1)).deleteAssignment(1L);
    }
}
