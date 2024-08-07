package com.shuaibu.service.impl;

import com.shuaibu.mapper.ReportSheetGradeMapper;
import com.shuaibu.model.SectionModel;
import com.shuaibu.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.ReportSheetGradeDto;
import com.shuaibu.model.ReportSheetGradeModel;
import com.shuaibu.repository.ReportSheetGradeRepository;
import com.shuaibu.service.ReportSheetGradeService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ReportSheetGradeMapper.*;

@Service
public class ReportSheetGradeImpl implements ReportSheetGradeService {

    private final SectionRepository sectionRepository;
    private final ReportSheetGradeRepository reportSheetGradeRepository;

    public ReportSheetGradeImpl(SectionRepository sectionRepository, ReportSheetGradeRepository reportSheetGradeRepository) {
        this.sectionRepository = sectionRepository;
        this.reportSheetGradeRepository = reportSheetGradeRepository;
    }

    @Override
    public List<ReportSheetGradeDto> getAllReportSheetGrades() {
        List<ReportSheetGradeModel> reportSheetGrades = reportSheetGradeRepository.findAll();
        return reportSheetGrades.stream().map(ReportSheetGradeMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ReportSheetGradeDto getReportSheetGradeById(Long id) {
        return mapToDto(reportSheetGradeRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveReportSheetGrade(ReportSheetGradeDto reportSheetGradeDto) {

        ReportSheetGradeModel reportSheetGradeModel = mapToModel(reportSheetGradeDto);

        // Fetch related entities from repositories
        SectionModel section = sectionRepository.findById(Long.parseLong(reportSheetGradeDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + reportSheetGradeDto.getSectionId()));
        // Set fetched entities to resultSetting model
        reportSheetGradeModel.setSectionId(section.getSectionName());

        reportSheetGradeRepository.save(reportSheetGradeModel);
    }

    @Override
    public void updateReportSheetGrade(ReportSheetGradeDto reportSheetGradeDto) {
        ReportSheetGradeModel reportSheetGradeModel = mapToModel(reportSheetGradeDto);

        // Fetch related entities from repositories
        SectionModel section = sectionRepository.findById(Long.parseLong(reportSheetGradeDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + reportSheetGradeDto.getSectionId()));
        // Set fetched entities to resultSetting model
        reportSheetGradeModel.setSectionId(section.getSectionName());

        reportSheetGradeRepository.save(reportSheetGradeModel);
    }
    
    @Override
    public void deleteReportSheetGrade(Long id) {
        reportSheetGradeRepository.deleteById(id);
    }
}
