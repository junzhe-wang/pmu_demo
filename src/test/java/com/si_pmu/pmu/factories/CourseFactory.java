package com.si_pmu.pmu.factories;

import com.si_pmu.pmu.models.Course;

import java.time.LocalDate;
import java.util.Set;

import static com.si_pmu.pmu.factories.PartantFactory.mockPartant;
import static com.si_pmu.pmu.factories.TestUtils.randomInt;
import static com.si_pmu.pmu.factories.TestUtils.randomString;

public class CourseFactory {
    public static Course mockCourse() {
        return Course.builder()
                .date(LocalDate.now())
                .nom(randomString("nom"))
                .numero(randomInt())
                .partants(Set.of(
                        mockPartant(1),
                        mockPartant(2),
                        mockPartant(3)
                ))
                .build();
    }
}
