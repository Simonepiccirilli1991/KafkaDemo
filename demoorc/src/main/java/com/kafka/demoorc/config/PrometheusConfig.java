package com.kafka.demoorc.config;

import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Configuration
public class PrometheusConfig {

    private static final String URI = "uri";
    private static Set<String> EXCLUDE_PATH_PREFIX = Set.of("/metrics", "/null", "/actuator", "/swagger-", "/v3");

    @Bean
    public MeterFilter denyUriMeterFilter() {
        return MeterFilter.deny(id -> {
            String uri = id.getTag(URI);
            return !ObjectUtils.isEmpty(uri) && EXCLUDE_PATH_PREFIX.stream().anyMatch(uri::startsWith);
        });
    }
}
