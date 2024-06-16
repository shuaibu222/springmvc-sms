package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;

public interface TermService {
    List<TermDto> getAllTerms();
    TermDto getTermById(UUID id);
    TermModel saveTerm(TermDto termDto);
    void updateTerm(TermDto termDto);
    void deleteTerm(UUID id);
}
