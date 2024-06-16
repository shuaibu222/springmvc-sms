package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.service.TermService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.TermMapper.*;

@Service
public class TermImpl implements TermService {
    
    private TermRepository termRepository;

    public TermImpl(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @Override
    public List<TermDto> getAllTerms() {
        List<TermModel> terms = termRepository.findAll();
        return terms.stream().map(term -> mapToDto(term)).collect(Collectors.toList());
    }

    @Override
    public TermDto getTermById(UUID id) {
        return mapToDto(termRepository.findById(id).get());
    }

    @Override
    public TermModel saveTerm(TermDto termDto) {
        return termRepository.save(mapToModel(termDto));
    }

    @Override
    public void updateTerm(TermDto termDto) {
        termRepository.save(mapToModel(termDto));
    }
    
    @Override
    public void deleteTerm(UUID id) {
        termRepository.deleteById(id);
    }
}
