package com.wolroys.wellbeing.event;

import com.wolroys.wellbeing.domain.event.EventRepository;
import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.service.EventServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockEventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void testThatAllEventWillBeReturned() {
        Event event = new Event();
        event.setTitle("1");

        Event event2 = new Event();
        event2.setTitle("2");

        Pageable unPaged = Pageable.unpaged();

        when(eventRepository.findAll(unPaged, null)).thenReturn(new PageImpl<>(List.of(event, event2)));

        List<EventDto> all = eventService.findAll(unPaged, null);

        Assertions.assertEquals(2, all.size());
    }
}
