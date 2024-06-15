package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.SessionModel;

public interface SessionService {
    List<SessionDto> getAllSessions();
    SessionDto getSessionById(UUID id);
    SessionModel saveSession(SessionDto sessionDto);
    void updateSession(SessionDto sessionDto);
    void deleteSession(UUID id);
}
