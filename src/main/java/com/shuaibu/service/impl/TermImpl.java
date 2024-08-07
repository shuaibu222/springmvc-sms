package com.shuaibu.service.impl;

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
        return terms.stream()
                .filter(t -> t.getIsActive().equals("True"))
                .map(term -> TermDto.builder()
                        .id(term.getId())
                        .termName(term.getTermName())
                        .isActive(term.getIsActive())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TermDto getTermById(Long id) {
        return mapToDto(termRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateTerm(TermDto termDto) {
        termRepository.save(mapToModel(termDto));
    }
    
    @Override
    public void deleteTerm(Long id) {
        termRepository.deleteById(id);
    }
}
