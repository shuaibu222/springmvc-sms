package com.shuaibu.mapper;

import com.shuaibu.dto.EventDto;
import com.shuaibu.model.EventModel;

public class EventMapper {
    
    public static EventDto mapToDto(EventModel eventModel){

        return EventDto.builder()
                .id(eventModel.getId())
                .eventTitle(eventModel.getEventTitle())
                .eventMessage(eventModel.getEventMessage())
                .timeAndDate(eventModel.getTimeAndDate())
        .build();
    }

    public static EventModel mapToModel(EventDto eventDto){

        return EventModel.builder()
                .id(eventDto.getId())
                .eventTitle(eventDto.getEventTitle())
                .eventMessage(eventDto.getEventMessage())
                .timeAndDate(eventDto.getTimeAndDate())
        .build();
    }
}
