package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.SessionModel;

public interface SessionService {
    List<SessionDto> getAllSessions();
    SessionDto getSessionById(Long id);
    SessionModel saveSession(SessionDto sessionDto);
    void updateSession(SessionDto sessionDto);
    void deleteSession(Long id);
}
