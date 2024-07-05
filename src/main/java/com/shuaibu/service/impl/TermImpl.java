package com.shuaibu.service.impl;

import com.shuaibu.mapper.TermMapper;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.service.TermService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.TermMapper.*;

@Service
public class TermImpl implements TermService {
    
    private final TermRepository termRepository;

    public TermImpl(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @Override
    public List<TermDto> getAllTerms() {
        List<TermModel> terms = termRepository.findAll();
        return terms.stream().map(TermMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public TermDto getTermById(Long id) {
        return mapToDto(termRepository.findById(id).get());
    }

    @Override
    public void saveTerm(TermDto termDto) {
        termRepository.save(mapToModel(termDto));
    }

    @Override
    public void updateTerm(TermDto termDto) {
        termRepository.save(mapToModel(termDto));
    }
    
    @Override
    public void deleteTerm(Long id) {
        termRepository.deleteById(id);
    }
}
