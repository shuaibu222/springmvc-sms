package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.StaffDto;

public interface StaffService {
    List<StaffDto> getAllStaffs();
    StaffDto getStaffById(Long id);
    void saveOrUpdateStaff(StaffDto staffDto);
    void deleteStaff(Long id);
}
