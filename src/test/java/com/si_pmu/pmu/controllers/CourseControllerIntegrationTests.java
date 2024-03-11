package com.si_pmu.pmu.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.si_pmu.pmu.models.Course;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.si_pmu.pmu.factories.CourseFactory.mockCourse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CourseControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private Course course;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        this.course = mockCourse();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldCreateCourseWithSuccess() throws Exception {
        final String json = this.objectMapper.writeValueAsString(this.course);

        this.mockMvc.perform(post("/api/v1/course")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldReturnBadRequestWhenAddingSameNumero() throws Exception {
        final String json = this.objectMapper.writeValueAsString(this.course);

        this.mockMvc.perform(post("/api/v1/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().is2xxSuccessful());

        this.mockMvc.perform(post("/api/v1/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }
}
