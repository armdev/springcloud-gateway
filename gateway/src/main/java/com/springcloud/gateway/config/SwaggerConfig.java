package com.springcloud.gateway.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("cloud api")
                .packagesToScan("com.springcloud")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public CommandLineRunner openApiGroups(
            RouteDefinitionLocator locator,
            SwaggerUiConfigParameters swaggerUiParameters) {
        return args -> Objects.requireNonNull(locator
                .getRouteDefinitions().collectList().block())
                .stream()
                .map(RouteDefinition::getId)
                .filter(id -> id.matches(".*-service"))
                .map(id -> id.replace("-service", ""))
                .forEach(swaggerUiParameters::addGroup);
    }
}
