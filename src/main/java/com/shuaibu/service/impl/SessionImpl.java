package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.SessionModel;
import com.shuaibu.repository.SessionRepository;
import com.shuaibu.service.SessionService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.SessionMapper.*;

@Service
public class SessionImpl implements SessionService {
    
    private SessionRepository sessionRepository;

    public SessionImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<SessionDto> getAllSessions() {
        List<SessionModel> sessions = sessionRepository.findAll();
        return sessions.stream().map(session -> mapToDto(session)).collect(Collectors.toList());
    }

    @Override
    public SessionDto getSessionById(Long id) {
        return mapToDto(sessionRepository.findById(id).get());
    }

    @Override
    public SessionModel saveSession(SessionDto sessionDto) {
        return sessionRepository.save(mapToModel(sessionDto));
    }

    @Override
    public void updateSession(SessionDto sessionDto) {
        sessionRepository.save(mapToModel(sessionDto));
    }
    
    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
