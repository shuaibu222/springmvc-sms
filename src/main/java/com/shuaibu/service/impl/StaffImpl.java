package com.shuaibu.service.impl;

import com.shuaibu.mapper.StaffMapper;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.repository.SchoolClassRepository;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.service.StaffService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.StaffMapper.*;

@Service
public class StaffImpl implements StaffService {

    private final SchoolClassRepository schoolClassRepository;
    private final StaffRepository staffRepository;

    public StaffImpl(SchoolClassRepository schoolClassRepository, StaffRepository staffRepository) {
        this.schoolClassRepository = schoolClassRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public List<StaffDto> getAllStaffs() {
        List<StaffModel> staffs = staffRepository.findAll();
        return staffs.stream().map(StaffMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public StaffDto getStaffById(Long id) {
        return mapToDto(staffRepository.findById(id).get());
    }

    @Override
    public void saveOrUpdateStaff(StaffDto staffDto) {

        StaffModel staffModel = staffRepository.save(mapToModel(staffDto));

        for (Long classIds: staffDto.getClassModelIds()) {
            SchoolClassModel schoolClassModel = schoolClassRepository.findById(classIds).get();
            schoolClassModel.getStaffModels().add(staffModel.getId());
            schoolClassRepository.save(schoolClassModel);
        }
    }
    
    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
