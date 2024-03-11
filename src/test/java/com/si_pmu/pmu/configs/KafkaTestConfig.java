package com.si_pmu.pmu.configs;

import com.si_pmu.pmu.models.Course;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;

@Profile("test")
@Configuration
public class KafkaTestConfig {

    @Bean
    public KafkaTemplate<String, Course> courseKafkaTemplate() {
        return Mockito.mock(KafkaTemplate.class);
    }
}
