package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;

public interface StaffService {
    List<StaffDto> getAllStaffs();
    StaffDto getStaffById(Long id);
    StaffModel saveOrUpdateStaff(StaffDto staffDto);
    void deleteStaff(Long id);
}
