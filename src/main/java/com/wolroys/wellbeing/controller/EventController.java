package com.wolroys.wellbeing.controller;

import com.wolroys.wellbeing.dto.EventDto;
import com.wolroys.wellbeing.dto.EventRequestDto;
import com.wolroys.wellbeing.service.EventService;
import com.wolroys.wellbeing.util.response.Response;
import com.wolroys.wellbeing.util.response.ResponseWithList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ResponseWithList<EventDto>> findAllEvents() {
        return ResponseEntity.ok(new ResponseWithList<EventDto>().foundWithPages(eventService.getAll()));
    }

    @PostMapping("/add")
    public ResponseEntity<Response<EventDto>> createEvent(@RequestBody EventRequestDto eventDto) {
        return ResponseEntity.ok(new Response<EventDto>().created(eventService.create(eventDto)));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response<EventDto>> editEvent(@PathVariable Long id, @RequestBody EventRequestDto updatedEvent) {
        return ResponseEntity.ok(new Response<EventDto>().updated(eventService.edit(id, updatedEvent)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<EventDto>> deleteEvent(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<EventDto>().deleted(eventService.deleteById(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<EventDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new Response<EventDto>().found(eventService.getById(id)));
    }
}
