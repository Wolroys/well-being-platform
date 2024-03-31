package com.wolroys.wellbeing.service;

import com.wolroys.wellbeing.entity.Event;
import com.wolroys.wellbeing.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;

    public List<Event> getAll(){
        return eventRepository.findAllByOrderByDateAsc();
    }


}
