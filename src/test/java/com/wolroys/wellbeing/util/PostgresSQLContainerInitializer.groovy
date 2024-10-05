package com.wolroys.wellbeing.util

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class PostgresSQLContainerInitializer {

    private static final PostgreSQLContainer container = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    )

    @BeforeAll
    static void beforeAll() {
        container.start()
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl)
        registry.add("spring.datasource.username", container::getUsername)
        registry.add("spring.datasource.password", container::getPassword)
    }

    @AfterAll
    static void afterAll() {
        container.stop()
    }
}
