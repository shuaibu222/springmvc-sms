package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.service.StaffService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.StaffMapper.*;

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
    public StaffDto getStaffById(Long id) {
        return mapToDto(staffRepository.findById(id).get());
    }

    @Override
    public StaffModel saveOrUpdateStaff(StaffDto staffDto) {
        return staffRepository.save(mapToModel(staffDto));
    }
    
    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
