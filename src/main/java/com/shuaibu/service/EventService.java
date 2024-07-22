package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.EventDto;

public interface EventService {
    List<EventDto> getAllEvent();
    EventDto getEventById(Long id);
    void saveOrUpdateEvent(EventDto eventDto);
    void deleteEvent(Long id);
}
