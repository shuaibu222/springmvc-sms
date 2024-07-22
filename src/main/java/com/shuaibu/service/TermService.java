package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.TermDto;

public interface TermService {
    List<TermDto> getAllTerms();
    TermDto getTermById(Long id);
    void saveOrUpdateTerm(TermDto termDto);
    void deleteTerm(Long id);
}
