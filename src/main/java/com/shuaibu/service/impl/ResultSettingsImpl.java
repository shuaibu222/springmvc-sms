package com.shuaibu.service.impl;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.*;
import com.shuaibu.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.repository.ResultSettingsRepository;
import com.shuaibu.service.ResultSettingsService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ResultSettingsMapper.mapToModel;
import static com.shuaibu.mapper.ResultSettingsMapper.*;

@Service
public class ResultSettingsImpl implements ResultSettingsService {
    
    private ResultSettingsRepository resultSettingsRepository;
    private SectionRepository sectionRepository;

    public ResultSettingsImpl(ResultSettingsRepository resultSettingsRepository, SectionRepository sectionRepository) {
        this.resultSettingsRepository = resultSettingsRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<ResultSettingsDto> getAllResultSettings() {
        List<ResultSettingsModel> resultSetting = resultSettingsRepository.findAll();
        return resultSetting.stream().map(grade -> mapToDto(grade)).collect(Collectors.toList());
    }

    @Override
    public ResultSettingsDto getResultSettingById(Long id) {
        return mapToDto(resultSettingsRepository.findById(id).get());
    }

    @Override
    public ResultSettingsModel saveOrUpdateResultSetting(ResultSettingsDto resultSettingDto) {
        // Map ResultSettingDto to ResultSettingModel
        ResultSettingsModel resultSettingModel = mapToModel(resultSettingDto);

        // Fetch related entities from repositories
        SectionModel section = sectionRepository.findById(Long.parseLong(resultSettingDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + resultSettingDto.getSectionId()));
        // Set fetched entities to resultSetting model
        resultSettingModel.setSectionId(section.getSectionName());

        // Save the resultSetting model
        return resultSettingsRepository.save(resultSettingModel);
    }
    
    @Override
    public void deleteResultSetting(Long id) {
        resultSettingsRepository.deleteById(id);
    }
}
