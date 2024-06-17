package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;

public interface TermService {
    List<TermDto> getAllTerms();
    TermDto getTermById(Long id);
    TermModel saveTerm(TermDto termDto);
    void updateTerm(TermDto termDto);
    void deleteTerm(Long id);
}
