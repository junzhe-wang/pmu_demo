package com.si_pmu.pmu.services.producers;

import com.si_pmu.pmu.models.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Course> courseKafkaTemplate;

    public void sendMessageWithCourseTopic(final Course course, final String topic) {
        final Message<Course> message = MessageBuilder
                .withPayload(course)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        courseKafkaTemplate.send(message);
    }
}
