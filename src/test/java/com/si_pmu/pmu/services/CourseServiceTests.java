package com.si_pmu.pmu.services;

import com.si_pmu.pmu.exceptions.CoursePayloadException;
import com.si_pmu.pmu.models.Course;
import com.si_pmu.pmu.repositories.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.si_pmu.pmu.factories.CourseFactory.mockCourse;
import static com.si_pmu.pmu.factories.TestUtils.randomInt;
import static com.si_pmu.pmu.factories.TestUtils.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    private CourseRepository repository;

    @InjectMocks
    private CourseService service;

    private Course successCourse;

    private Course failureCourse;

    @BeforeEach
    public void setup() {
        successCourse = mockCourse();

        failureCourse = Course.builder()
                                .nom(randomString("nom"))
                                .date(LocalDate.now())
                                .partants(Set.of())
                                .numero(randomInt())
                                .build();
    }

    @Test
    @DisplayName("should ingest course with success")
    public void shouldIngestCourseWithSuccess() {

        when(repository.findCourseByDateAndNumero(successCourse.getDate(), successCourse.getNumero())).thenReturn(Optional.empty());
        when(repository.save(successCourse)).thenReturn(successCourse);

        final Course result = service.saveCourse(successCourse);

        assertEquals(result, successCourse);
    }

    @Test
    @DisplayName("should throw course payload exception when ingesting course with same numero")
    public void shouldThrowCoursePayLoadExceptionWhenIngestCourseWithSameNumero() {
        when(repository.findCourseByDateAndNumero(failureCourse.getDate(), failureCourse.getNumero()))
                .thenReturn(Optional.of(new Course()));

        final CoursePayloadException thrown = Assertions.assertThrows(CoursePayloadException.class, () -> {
            service.saveCourse(failureCourse);
        });

        assertTrue(thrown.getMessage().contains("The number of the course has been registered"));
    }
}
