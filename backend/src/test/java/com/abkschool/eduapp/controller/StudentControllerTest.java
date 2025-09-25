package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.student.StudentDTO;
import com.abkschool.eduapp.service.StudentService;
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

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {StudentController.class, StudentControllerTest.MockConfig.class})
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;
    @TestConfiguration
    static class MockConfig {
        @Bean
        StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        // DTO nesnelerini setter ile hazırla
        StudentDTO s1 = new StudentDTO();
        s1.setId(1L);
        s1.setFirstName("Ali");
        s1.setLastName("Yılmaz");
        s1.setEmail("ali@example.com");

        StudentDTO s2 = new StudentDTO();
        s2.setId(2L);
        s2.setFirstName("Ayşe");
        s2.setLastName("Demir");
        s2.setEmail("ayse@example.com");

        List<StudentDTO> students = List.of(s1, s2);

        Mockito.when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ali"))
                .andExpect(jsonPath("$[1].firstName").value("Ayşe"));
    }
}
