package com.si_pmu.pmu.repositories;

import com.si_pmu.pmu.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByDate(final LocalDate date);

    Optional<Course> findCourseByDateAndNumero(final LocalDate date,
                                               final Integer numero);
}
