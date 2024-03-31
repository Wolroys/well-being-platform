package com.wolroys.wellbeing;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class RepositoryTest {

    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>();
}
