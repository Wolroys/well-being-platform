package com.wolroys.wellbeing.event;

import com.wolroys.wellbeing.domain.event.EventRepository;
import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.service.EventServiceImpl;
import com.wolroys.wellbeing.util.PostgresSQLContainerInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class EventServiceImplTest extends PostgresSQLContainerInitializer {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventServiceImpl eventService;

    @Test
    void checkThatAllElementsWillReturn() {
        Event event1 = new Event();
        event1.setTitle("Event 1");
        Event event2 = new Event();
        event2.setTitle("Event 2");

        eventRepository.saveAllAndFlush(List.of(event1, event2));

        List<EventDto> founded = eventService.findAll(Pageable.unpaged(), null);

        Assertions.assertEquals(2, founded.size());
    }

    @Test
    void repositoryShouldReturnOnlyOneEntityByTitle() {
        Event event1 = new Event();
        event1.setTitle("Event 1");
        Event event2 = new Event();
        event2.setTitle("Event 2");
        eventRepository.saveAll(List.of(event1, event2));

        Page<Event> events = eventRepository.findAll(Pageable.unpaged(), "1");

        Assertions.assertEquals(1, events.getTotalElements());
    }
}
