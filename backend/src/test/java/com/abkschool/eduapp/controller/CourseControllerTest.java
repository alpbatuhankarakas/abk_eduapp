package com.abkschool.eduapp.controller;

import com.abkschool.eduapp.domain.dto.course.CourseDTO;
import com.abkschool.eduapp.service.CourseService;
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

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {CourseController.class, CourseControllerTest.MockConfig.class})
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        CourseService courseService() {
            return Mockito.mock(CourseService.class);
        }
    }

    @Test
    void shouldReturnAllCourses() throws Exception {
        CourseDTO c1 = new CourseDTO();
        c1.setId(1L);
        c1.setName("Matematik");

        CourseDTO c2 = new CourseDTO();
        c2.setId(2L);
        c2.setName("Fizik");

        List<CourseDTO> courses = List.of(c1, c2);

        Mockito.when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/courses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Matematik"))
                .andExpect(jsonPath("$[1].name").value("Fizik"));
    }
}
