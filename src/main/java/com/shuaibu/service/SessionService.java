package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SessionDto;

public interface SessionService {
    List<SessionDto> getAllSessions();
    SessionDto getSessionById(Long id);
    void saveOrUpdateSession(SessionDto sessionDto);
    void deleteSession(Long id);
}
