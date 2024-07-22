package com.shuaibu.mapper;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.SessionModel;

public class SessionMapper {
    
    public static SessionDto mapToDto(SessionModel sessionModel){

        return SessionDto.builder()
                .id(sessionModel.getId())
                .sessionName(sessionModel.getSessionName())
                .isActive(sessionModel.getIsActive())
        .build();
    }

    public static SessionModel mapToModel(SessionDto sessionDto){

        return SessionModel.builder()
                .id(sessionDto.getId())
                .sessionName(sessionDto.getSessionName())
                .isActive(sessionDto.getIsActive())
        .build();
    }
}
