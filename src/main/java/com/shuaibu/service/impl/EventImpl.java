package com.shuaibu.service.impl;

import com.shuaibu.mapper.EventMapper;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.EventDto;
import com.shuaibu.model.EventModel;
import com.shuaibu.repository.EventRepository;
import com.shuaibu.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.EventMapper.*;

@Service
public class EventImpl implements EventService {
    
    private final EventRepository eventRepository;

    public EventImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventDto> getAllEvent() {
        List<EventModel> sessions = eventRepository.findAll();
        return sessions.stream().map(EventMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(Long id) {
        return mapToDto(eventRepository.findById(id).get());
    }

    @Override
    public void saveOrUpdateEvent(EventDto eventDto) {
        eventRepository.save(mapToModel(eventDto));
    }
    
    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
