package com.shuaibu.service.impl;

import com.shuaibu.dto.TermDto;
import com.shuaibu.mapper.SessionMapper;
import com.shuaibu.model.TermModel;
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
    
    private final SessionRepository sessionRepository;

    public SessionImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<SessionDto> getAllSessions() {
        List<SessionModel> sessionModels = sessionRepository.findAll();
        return sessionModels.stream()
                .filter(s -> s.getIsActive().equals("True"))
                .map(SessionMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SessionDto getSessionById(Long id) {
        return mapToDto(sessionRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateSession(SessionDto sessionDto) {
        sessionRepository.save(mapToModel(sessionDto));
    }
    
    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
