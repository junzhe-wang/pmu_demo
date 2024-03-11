package com.si_pmu.pmu.services;

import com.si_pmu.pmu.exceptions.CoursePayloadException;
import com.si_pmu.pmu.models.Course;
import com.si_pmu.pmu.models.Partant;
import com.si_pmu.pmu.repositories.CourseRepository;
import com.si_pmu.pmu.repositories.PartantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final PartantRepository partantRepository;

    /**
     *
     * @param course: the course to be created
     * @return the created course with ID
     */
    @Transactional
    public Course saveCourse(final Course course) {
        if (findCourseByDateAndNumero(course.getDate(), course.getNumero()).isPresent()) {
            throw new CoursePayloadException(
                    String.format("The number of the course has been registered at %s", course.getDate())
            );
        }
        this.checkPartantNumero(course.getPartants());
        this.addCourseToPartant(course);

        return courseRepository.save(course);
    }

    /**
     *
     * @param partants check if the Partant Set has at least 3 elements and the numeros for Partants are consistent
     */
    private void checkPartantNumero(final Set<Partant> partants) {
        final boolean[] numeros = new boolean[partants.size() + 1];

        for (Partant p : partants) {
            if (numeros[p.getNumero()]) {
                throw new CoursePayloadException(
                        String.format("The number %d for the partant has been duplicated", p.getNumero())
                );
            }
            numeros[p.getNumero()] = true;
        }

        for (int i=1; i<numeros.length; i++) {
            if (!numeros[i]) {
                throw new CoursePayloadException(
                        String.format("The number %d for the partant is missing", i)
                );
            }
        }
    }

    private void addCourseToPartant(final Course course) {
        course.getPartants().forEach(partant -> {
            partant.setCourse(course);
            partant.setDate(course.getDate());
        });
    }

    @Transactional
    public Course updateCourseById(final Course updatedCourse) {
        return courseRepository.findById(updatedCourse.getId()).map(course -> {
            final List<Integer> oldPartantId = course.getPartants().stream().map(Partant::getId).toList();
            partantRepository.deleteAllById(oldPartantId);
            course.setPartants(updatedCourse.getPartants());
            addCourseToPartant(course);
            course.setPartants(updatedCourse.getPartants());
            course.setDate(updatedCourse.getDate());
            course.setNom(updatedCourse.getNom());
            return courseRepository.save(course);
        }).orElseThrow(() -> new EntityNotFoundException(String.format("Course not found with id: %s", updatedCourse.getId())));
    }

    public List<Course> findAllByDate(final LocalDate date) {
        return courseRepository.findAllByDate(date);
    }

    public Optional<Course> findCourseByDateAndNumero(final LocalDate date, final Integer numero) {
        return courseRepository.findCourseByDateAndNumero(date, numero);
    }
}
