package com.wolroys.wellbeing.domain.event.controller;

import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.entity.EventRequestDto;
import com.wolroys.wellbeing.domain.event.service.EventService;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ResponseWithList<EventDto>> findAllEvents(@PageableDefault(sort = {"date"},
            direction = Sort.Direction.ASC, size = 8) Pageable pageable,
                                                                    @RequestParam(required = false) String title) {
        return ResponseEntity.ok(new ResponseWithList<EventDto>().foundWithPages(eventService.findAll(pageable, title)));
    }

    @GetMapping("/info")
    public ResponseEntity<Response<EventDto>> findById(@RequestParam Long eventId) {
        return ResponseEntity.ok(new Response<EventDto>().found(eventService.findById(eventId)));
    }

    @PostMapping("/add")
    public ResponseEntity<Response<EventDto>> createEvent(@RequestBody EventRequestDto eventDto) {
        return ResponseEntity.ok(new Response<EventDto>().created(eventService.create(eventDto)));
    }

    @PutMapping("/edit")
    public ResponseEntity<Response<EventDto>> editEvent(@RequestBody EventRequestDto updatedEvent) {
        return ResponseEntity.ok(new Response<EventDto>().updated(eventService.edit(updatedEvent)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<EventDto>> deleteEvent(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<EventDto>().deleted(eventService.deleteById(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<EventDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<EventDto>().found(eventService.findById(id)));
    }
}
