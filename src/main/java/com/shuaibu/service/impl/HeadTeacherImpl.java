package com.shuaibu.service.impl;

import com.shuaibu.dto.*;
import com.shuaibu.mapper.*;
import com.shuaibu.model.HeadTeacherModel;
import com.shuaibu.repository.*;
import com.shuaibu.service.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.HeadTeacherMapper.mapToDto;
import static com.shuaibu.mapper.HeadTeacherMapper.mapToModel;

@Service
public class HeadTeacherImpl implements HeadTeacherService {

    private final StaffService staffService;
    private final SectionService sectionService;
    private final StaffRepository staffRepository;
    private final HeadTeacherRepository headTeacherRepository;
    private final HeadTeacherCommentRepository headTeacherCommentRepository;
    private final SectionRepository sectionRepository;

    public HeadTeacherImpl(HeadTeacherRepository headTeacherRepository, StaffService staffService, SectionService sectionService, StaffRepository staffRepository, HeadTeacherCommentRepository headTeacherCommentRepository, SectionRepository sectionRepository) {
        this.staffService = staffService;
        this.sectionService = sectionService;
        this.staffRepository = staffRepository;
        this.headTeacherRepository = headTeacherRepository;
        this.headTeacherCommentRepository = headTeacherCommentRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<HeadTeacherDto> getAllHeadTeachers() {
        List<HeadTeacherModel> headTeachers = headTeacherRepository.findAll();
        return headTeachers.stream().map(HeadTeacherMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public HeadTeacherDto getHeadTeacherById(Long id) {
        return mapToDto(headTeacherRepository.findById(id).orElseThrow());
    }

    @Transactional
    @Override
    public void saveOrUpdateHeadTeacher(HeadTeacherDto headTeacherDto) {
        boolean isNew = headTeacherDto.getId() == null;

        // Fetch the staff and class details based on IDs
        StaffDto staffDto = staffService.getStaffById(Long.valueOf(headTeacherDto.getTeacherId()));
        SectionDto sectionDto = sectionService.getSectionById(Long.valueOf(headTeacherDto.getSectionId()));

        if (!isNew) {
            // Clear previous links
            HeadTeacherModel existingHeadTeacher = headTeacherRepository.findById(headTeacherDto.getId()).orElseThrow();
            StaffDto existingStaffDto = staffService.getStaffById(Long.valueOf(existingHeadTeacher.getTeacherId()));
            SectionDto existingSection = sectionService.getSectionById(Long.valueOf(existingHeadTeacher.getSectionId()));

            existingSection.setHeadTeacherId(null);
            sectionRepository.save(SectionMapper.mapToModel(existingSection));

            existingStaffDto.setHeadTeacherOfId(null);
            staffRepository.save(StaffMapper.mapToModel(existingStaffDto));

            // delete all comments for that teacher
            headTeacherCommentRepository.deleteByTeacherId(existingHeadTeacher.getTeacherId());
        }

        // Link the class with the teacher ID as class teacher
        sectionDto.setHeadTeacherId(String.valueOf(staffDto.getId()));
        sectionRepository.save(SectionMapper.mapToModel(sectionDto));

        // Link the teacher with the class ID as class teacher
        staffDto.setHeadTeacherOfId(String.valueOf(sectionDto.getId()));
        staffRepository.save(StaffMapper.mapToModel(staffDto));

        // Set the teacher name and class name in the DTO
        headTeacherDto.setTeacherName(staffDto.getFirstName() + " " + staffDto.getLastName());
        headTeacherDto.setSectionName(sectionDto.getSectionName());

        // Save the class teacher model
        headTeacherRepository.save(mapToModel(headTeacherDto));
    }



    @Transactional
    @Override
    public void deleteHeadTeacher(Long id) {
        HeadTeacherModel headTeacher = headTeacherRepository.findById(id).orElseThrow();
        // delete all comments for that teacher
        headTeacherCommentRepository.deleteByTeacherId(headTeacher.getTeacherId());

        headTeacherRepository.deleteById(id);
    }
}
