package com.shuaibu.controller;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.service.SchoolClassService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final StaffRepository staffRepository;
    private final SchoolClassService schoolClassService;
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    public GlobalControllerAdvice(StaffRepository staffRepository, SchoolClassService schoolClassService) {
        this.staffRepository = staffRepository;
        this.schoolClassService = schoolClassService;
    }

    @ModelAttribute
    public void populateSections(Model model) {
        List<SchoolClassDto> classes = schoolClassService.getAllSchoolClass();
        List<SchoolClassDto> staffClasses = new ArrayList<>();

        // for extracting user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Retrieve username directly
            logger.info("Authenticated username: {}", username);

            StaffModel staffModel = staffRepository.findByUserName(username);
            if (staffModel != null) {
                logger.info("Found UserModel: {}", staffModel);

                // Filter classes to find all matching staffClasses
                for (SchoolClassDto classDto : classes) {
                    // Assuming classDto.getStaffModels() returns a list of user IDs
                    if (classDto.getStaffModels().contains(staffModel.getId())) {
                        staffClasses.add(classDto);
                    }
                }
            }
        } else {
            logger.warn("Authentication is null or not authenticated");
        }

        model.addAttribute("classes", staffClasses);
    }

    @ModelAttribute
    public void populateClasses(Model model) {
        List<SchoolClassDto> classes = schoolClassService.getAllSchoolClass();
        List<SchoolClassDto> staffClasses = new ArrayList<>();

        // for extracting user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Retrieve username directly
            logger.info("Authenticated username: {}", username);

            StaffModel staffModel = staffRepository.findByUserName(username);
            if (staffModel != null) {
                logger.info("Found UserModel: {}", staffModel);

                // Filter classes to find all matching staffClasses
                for (SchoolClassDto classDto : classes) {
                    if (classDto.getClassTeacher() != null && classDto.getClassTeacher().equals(String.valueOf(staffModel.getId()))) {
                        staffClasses.add(classDto);
                    }
                    logger.error("No class for class teacher!");
                }
            }
        } else {
            logger.warn("Authentication is null or not authenticated");
        }

        model.addAttribute("staffClasses", staffClasses);
    }
}
