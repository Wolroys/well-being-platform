package com.wolroys.wellbeing.event;

import com.wolroys.wellbeing.domain.event.EventMapper;
import com.wolroys.wellbeing.domain.event.EventRepository;
import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.service.EventServiceImpl;
import com.wolroys.wellbeing.util.PostgresSQLContainerInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MockEventServiceTest extends PostgresSQLContainerInitializer {

    @Spy
    private EventMapper eventMapper;

    @Spy
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void testThatAllEventWillBeReturned() {
        Event event = new Event();
        event.setTitle("1");

        Event event2 = new Event();
        event2.setTitle("2");

        eventRepository.saveAllAndFlush(List.of(event, event2));

        List<EventDto> all = eventService.findAll(Pageable.unpaged(), null);

        Assertions.assertEquals(2, all.size());
    }
}
