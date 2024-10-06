package com.wolroys.wellbeing.event;

import com.wolroys.wellbeing.domain.event.EventRepository;
import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.entity.EventRequestDto;
import com.wolroys.wellbeing.domain.event.entity.Status;
import com.wolroys.wellbeing.domain.event.service.EventServiceImpl;
import com.wolroys.wellbeing.domain.user.entity.User;
import com.wolroys.wellbeing.util.PostgreSQLContainerInitializer;
import com.wolroys.wellbeing.util.exception.EventNotFoundException;
import com.wolroys.wellbeing.util.exception.IncorrectDateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class EventServiceImplTest extends PostgreSQLContainerInitializer {

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

        Assertions.assertFalse(founded.isEmpty());
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

    @Test
    void testShouldThrowException() {
        Assertions.assertThrows(EventNotFoundException.class, () -> eventService.findById(1001L),
                "This event doesn't exist");
    }

    @Test
    void testShouldReturnSavedEvent() {
        Event event1 = new Event();
        event1.setTitle("Saved entity");

        Event event = eventRepository.saveAndFlush(event1);

        eventService.findById(event.getId());
        Assertions.assertNotNull(event);
        Assertions.assertEquals(event1.getTitle(), event.getTitle());
    }

    @Test
    void eventShouldBeSaved() {
        EventRequestDto eventRequestDto = new EventRequestDto()
                .setTitle("Title")
                .setDescription("Description")
                .setUrl("URL")
                .setStatus(Status.PLANNED)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusHours(1));

        User user = new User();
        user.setEmail("test@mail.ru");
        user.setId(1L);

        EventDto eventDto = eventService.create(eventRequestDto);

        Assertions.assertNotNull(eventDto);
        Assertions.assertEquals(eventRequestDto.getTitle(), eventDto.getTitle());
        Assertions.assertEquals(eventRequestDto.getDescription(), eventDto.getDescription());
        Assertions.assertEquals(eventRequestDto.getUrl(), eventDto.getUrl());
        Assertions.assertEquals(eventRequestDto.getStatus(), eventDto.getStatus());
        Assertions.assertEquals(eventRequestDto.getStartDate(), eventDto.getStartDate());
        Assertions.assertEquals(eventRequestDto.getEndDate(), eventDto.getEndDate());
    }

    @Test
    void testShouldReturnExceptionThatStartDateAfterEndDate() {
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setTitle("Title");
        eventRequestDto.setStartDate(LocalDateTime.now().plusHours(1));
        eventRequestDto.setEndDate(LocalDateTime.now());

        Assertions.assertThrows(IncorrectDateException.class, () -> eventService.create(eventRequestDto),
                "Start date cannot be after end date");
    }
}
