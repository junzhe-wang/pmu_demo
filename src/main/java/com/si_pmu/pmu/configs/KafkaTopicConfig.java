package com.si_pmu.pmu.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String COURSE_CREATION_TOPIC = "course-creation";
    public static final String COURSE_UPDATE_TOPIC = "course-update";

    @Bean
    public NewTopic courseCreationTopic() {
        return TopicBuilder
                .name(COURSE_CREATION_TOPIC)
                .build();
    }

    @Bean
    public NewTopic courseUpdateTopic() {
        return TopicBuilder
                .name(COURSE_UPDATE_TOPIC)
                .build();
    }
}
