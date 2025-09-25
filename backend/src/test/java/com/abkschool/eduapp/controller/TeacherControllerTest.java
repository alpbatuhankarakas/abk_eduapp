package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.teacher.TeacherDTO;
import com.abkschool.eduapp.service.TeacherService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {TeacherController.class, TeacherControllerTest.MockConfig.class})
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherService teacherService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        TeacherService teacherService() {
            return Mockito.mock(TeacherService.class);
        }
    }

    @Test
    void shouldReturnAllTeachers() throws Exception {
        TeacherDTO t1 = new TeacherDTO();
        t1.setId(1L);
        t1.setFirstName("Mehmet");
        t1.setLastName("Kaya");

        TeacherDTO t2 = new TeacherDTO();
        t2.setId(2L);
        t2.setFirstName("Zeynep");
        t2.setLastName("Arslan");

        List<TeacherDTO> teachers = List.of(t1, t2);

        Mockito.when(teacherService.getAllTeachers()).thenReturn(teachers);

        mockMvc.perform(get("/api/teachers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Mehmet"))
                .andExpect(jsonPath("$[1].firstName").value("Zeynep"));
    }
}
