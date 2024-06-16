package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.model.SubjectModel;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.service.StaffService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.StaffMapper.*;
import static com.shuaibu.mapper.SubjectMapper.*;

@Service
public class StaffImpl implements StaffService {
    
    private StaffRepository staffRepository;

    public StaffImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public List<StaffDto> getAllStaffs() {
        List<StaffModel> staffs = staffRepository.findAll();
        return staffs.stream().map(staff -> mapToDto(staff)).collect(Collectors.toList());
    }

    @Override
    public StaffDto getStaffById(UUID id) {
        return mapToDto(staffRepository.findById(id).get());
    }

    @Override
    public StaffModel saveStaff(StaffDto staffDto) {
        StaffModel staffModel = mapToModel(staffDto);
        
        return staffRepository.save(staffModel);
    }

    @Override
    public void updateStaff(StaffDto staffDto) {
        StaffModel staffModel = mapToModel(staffDto);
        
        staffRepository.save(staffModel);
    }
    
    @Override
    public void deleteStaff(UUID id) {
        staffRepository.deleteById(id);
    }
}
