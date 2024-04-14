package com.wolroys.wellbeing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolroys.wellbeing.dto.EventRequestDto;
import com.wolroys.wellbeing.entity.Event;
import com.wolroys.wellbeing.entity.Status;
import com.wolroys.wellbeing.repository.EventRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
public class RepositoryTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mockMvc;
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:9.6.12");

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        eventRepository.deleteAll();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldGetAllEvents() {
        List<Event> events = List.of(
                new Event(null, "2313", "dsadas", LocalDateTime.now(), "dsa", "dsa", Status.PLANNED),
                new Event(null, "fds2313", "dsadasfdsfdsf", LocalDateTime.now(), "dfdsfdssa", "dsafdsfsd", Status.PLANNED)
        );

        eventRepository.saveAll(events);

        System.out.println(eventRepository.findAll());
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/event")
                .then()
                .statusCode(200)
                .body(".", Matchers.hasSize(2));
    }

    @Test
    @SneakyThrows
    void saveCars() {

        Map<String, String> map = Map.of(
                "title", "gdffsdfsa",
                "description", "dsadsa321",
                "date", "2023-03-15T12:34:56",
                "speaker", "dsadasdas",
                "link", "31jfsd",
                "status", "planned"
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        var result = mockMvc.perform(post("http://localhost:8080/event/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Event> events = eventRepository.findAll();

        assertThat(events).isNotEmpty();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(json);

    }
}
