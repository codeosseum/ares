package com.codeosseum.ares.eventbus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ObjectMapper eventMapperObjectMapper() {
        return new ObjectMapper();
    }
}
