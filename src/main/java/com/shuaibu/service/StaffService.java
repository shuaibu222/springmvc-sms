package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;

public interface StaffService {
    List<StaffDto> getAllStaffs();
    StaffDto getStaffById(UUID id);
    StaffModel saveStaff(StaffDto staffDto);
    void updateStaff(StaffDto staffDto);
    void deleteStaff(UUID id);
}
