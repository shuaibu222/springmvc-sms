package com.shuaibu.mapper;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.SessionModel;

public class SessionMapper {
    
    public static SessionDto mapToDto(SessionModel sessionModel){
        SessionDto sessionDto = SessionDto.builder()
        .id(sessionModel.getId())
        .sessionName(sessionModel.getSessionName())
        .build();

        return sessionDto;
    }

    public static SessionModel mapToModel(SessionDto sessionDto){
        SessionModel sessionModel =SessionModel.builder()
        .id(sessionDto.getId())
        .sessionName(sessionDto.getSessionName())
        .build();

        return sessionModel;
    }
}
