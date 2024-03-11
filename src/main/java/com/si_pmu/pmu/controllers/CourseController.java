package com.si_pmu.pmu.controllers;

import com.si_pmu.pmu.models.Course;
import com.si_pmu.pmu.services.CourseService;
import com.si_pmu.pmu.services.producers.KafkaProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.si_pmu.pmu.configs.KafkaTopicConfig.COURSE_CREATION_TOPIC;
import static com.si_pmu.pmu.configs.KafkaTopicConfig.COURSE_UPDATE_TOPIC;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;
    private final KafkaProducer producer;

    /**
     *
     * @param course: the course to be created
     * @return the created course with ID
     */
    @PostMapping
    public ResponseEntity<Course> save(@Valid @RequestBody final Course course) {
        final Course saved = service.saveCourse(course);

        producer.sendMessageWithCourseTopic(saved, COURSE_CREATION_TOPIC);
        return new ResponseEntity<>(
                saved,
                HttpStatus.CREATED
        );
    }

    /**
     *
     * @param course the course to be updated with the provided ID
     * @return the updated course with ID
     */

    @PutMapping
    public ResponseEntity<Course> update(@Valid @RequestBody final Course course) {
        final Course updated = service.updateCourseById(course);

        producer.sendMessageWithCourseTopic(updated, COURSE_UPDATE_TOPIC);
        return new ResponseEntity<>(
                updated,
                HttpStatus.OK
        );
    }

    @GetMapping()
    public List<Course> findAllByDate(@RequestParam final LocalDate date) {
        return service.findAllByDate(date);
    }
}
