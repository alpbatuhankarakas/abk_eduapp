package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentCreateRequest;
import com.abkschool.eduapp.domain.dto.enrollment.EnrollmentDTO;
import com.abkschool.eduapp.service.EnrollmentService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {EnrollmentController.class, EnrollmentControllerTest.MockConfig.class})
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentService enrollmentService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        EnrollmentService enrollmentService() {
            return Mockito.mock(EnrollmentService.class);
        }
    }

    @Test
    void shouldReturnAllEnrollments() throws Exception {
        EnrollmentDTO e1 = new EnrollmentDTO();
        e1.setId(1L);
        e1.setStudentId(42L);
        e1.setCourseId(100L);
        e1.setStatus("ENROLLED");

        EnrollmentDTO e2 = new EnrollmentDTO();
        e2.setId(2L);
        e2.setStudentId(43L);
        e2.setCourseId(200L);
        e2.setStatus("ENROLLED");

        List<EnrollmentDTO> enrollments = List.of(e1, e2);

        Mockito.when(enrollmentService.getAllEnrollments()).thenReturn(enrollments);

        mockMvc.perform(get("/api/enrollments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value(42L))
                .andExpect(jsonPath("$[1].studentId").value(43L));
    }

    @Test
    void shouldReturnEnrollmentById() throws Exception {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(1L);
        dto.setStudentId(42L);
        dto.setCourseId(100L);

        Mockito.when(enrollmentService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/enrollments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(42L))
                .andExpect(jsonPath("$.courseId").value(100L));
    }
    @Test
    void shouldCreateEnrollment() throws Exception {
        EnrollmentCreateRequest request = new EnrollmentCreateRequest();
        request.setStudentId(42L);
        request.setCourseId(100L);

        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(1L);
        dto.setStudentId(42L);
        dto.setCourseId(100L);

        Mockito.when(enrollmentService.createEnrollment(any(EnrollmentCreateRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"studentId":42,"courseId":100}
                            """))
                .andExpect(status().isCreated())   // ✅ değiştirildi
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.studentId").value(42L))
                .andExpect(jsonPath("$.courseId").value(100L));
    }

    @Test
    void shouldDeleteEnrollment() throws Exception {
        Mockito.doNothing().when(enrollmentService).deleteEnrollment(1L);

        mockMvc.perform(delete("/api/enrollments/1"))
                .andExpect(status().isNoContent());  // ✅ değiştirildi

        Mockito.verify(enrollmentService, Mockito.times(1)).deleteEnrollment(1L);
    }

}
