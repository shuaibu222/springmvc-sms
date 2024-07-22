package com.shuaibu.service.impl;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.dto.ResultCheckingDto;
import com.shuaibu.mapper.ResultMapper;
import com.shuaibu.model.StudentModel;
import com.shuaibu.repository.ResultRepository;
import com.shuaibu.repository.StudentRepository;
import com.shuaibu.service.ResultCheckingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ResultCheckingImpl implements ResultCheckingService {

    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResultCheckingImpl.class);

    public ResultCheckingImpl(ResultRepository resultRepository, StudentRepository studentRepository) {
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ResultDto searchResult(ResultCheckingDto resultCheckingDto) {

        StudentModel authenticatedStudent = getAuthenticatedStudent();
        if (authenticatedStudent == null) {
            throw new IllegalArgumentException("User not authenticated or not found.");
        }

        // Find the student with that regNo and password
        StudentModel studentModel = studentRepository.findByRegNoAndPassword(resultCheckingDto.getRegNo(), authenticatedStudent.getPassword());
        if (studentModel == null) {
            throw new IllegalArgumentException("Invalid registration number or password.");
        }

        return ResultMapper.mapToDto(resultRepository.findByRegNo(studentModel.getRegNo()));
    }

    private StudentModel getAuthenticatedStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("Authentication is null or not authenticated");
            return null;
        }

        String username = authentication.getName();
        StudentModel studentModel = studentRepository.findByUserName(username);
        if (studentModel == null) {
            logger.warn("UserModel not found for username: {}", username);
            return null;
        } else {
            logger.info("Found UserModel: {}", studentModel);
            return studentModel;
        }
    }
}
