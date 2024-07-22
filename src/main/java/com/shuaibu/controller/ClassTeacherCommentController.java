package com.shuaibu.controller;

import com.shuaibu.dto.ClassTeacherDto;
import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.dto.StaffDto;
import com.shuaibu.mapper.SchoolClassMapper;
import com.shuaibu.mapper.StaffMapper;
import com.shuaibu.model.ClassTeacherModel;
import com.shuaibu.repository.ClassTeacherCommentRepository;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.service.SchoolClassService;
import com.shuaibu.service.StaffService;
import com.shuaibu.service.impl.StaffImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.ClassTeacherCommentDto;
import com.shuaibu.model.ClassTeacherCommentModel;
import com.shuaibu.service.ClassTeacherCommentService;

import jakarta.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/classTeacherComments")
@PreAuthorize("hasRole('ADMIN')")
public class ClassTeacherCommentController {
    
    private final ClassTeacherCommentService classTeacherCommentService;
    private final StaffService staffService;
    private final SchoolClassService schoolClassService;
    private final ClassTeacherCommentRepository classTeacherCommentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StaffRepository staffRepository;

    public ClassTeacherCommentController(ClassTeacherCommentService classTeacherCommentService, StaffService staffService, SchoolClassService schoolClassService, ClassTeacherCommentRepository classTeacherCommentRepository, SchoolClassRepository schoolClassRepository, StaffRepository staffRepository) {
        this.classTeacherCommentService = classTeacherCommentService;
        this.staffService = staffService;
        this.schoolClassService = schoolClassService;
        this.classTeacherCommentRepository = classTeacherCommentRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.staffRepository = staffRepository;
    }

    @GetMapping
    public String assignClassTeacherForm(Model model) {
        model.addAttribute("classTeacherDto", new ClassTeacherModel());
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("classes", schoolClassService.getAllSchoolClass());
        model.addAttribute("classTeacherComments", classTeacherCommentService.getAllClassTeacherComments());
        return "classTeacherComments/list";
    }

//    TODO: edit class teacher, and create new repo for class teacher

    @PostMapping
    public String saveAssignClassTeacher(@Valid @ModelAttribute("classTeacherDto") ClassTeacherDto classTeacherDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staffs", staffService.getAllStaffs());
            model.addAttribute("classes", schoolClassService.getAllSchoolClass());
            model.addAttribute("classTeacherComments", classTeacherCommentService.getAllClassTeacherComments());
            return "classTeacherComments/list";
        }

        // for linking class and class teacher
//        StaffDto staffDto = staffService.getStaffById(Long.valueOf(classTeacherDto.getTeacherName()));
//        SchoolClassDto schoolClassDto = schoolClassService.getSchoolClassById(Long.valueOf(classTeacherDto.getClassName()));
//
//        // link class with teacher id as class teacher
//        schoolClassDto.setClassTeacher(String.valueOf(staffDto.getId()));
//        schoolClassRepository.save(SchoolClassMapper.mapToModel(schoolClassDto));
//
//        // link teacher with class id as class teacher
//        staffDto.setClassTeacherOfId(String.valueOf(schoolClassDto.getId()));
//        staffRepository.save(StaffMapper.mapToModel(staffDto));
//
//        // save it to class teacher repo
//        ClassTeacherCommentModel classTeacherCommentModel = new ClassTeacherCommentModel();
//        classTeacherCommentModel.setTeacherName(staffDto.getFirstName() + " " + staffDto.getLastName());
//        classTeacherCommentModel.setClassName(schoolClassDto.getSectionId() + " " + schoolClassDto.getClassName());
//        classTeacherCommentRepository.save(classTeacherCommentModel);

        return "redirect:/classTeacherComments";
    }

    // TODO: update comment
    // we don't need new because we will be updating all the time
    @GetMapping("/edit/{id}")
    public String updateClassTeacherCommentForm(@PathVariable Long id, Model model) {
        ClassTeacherCommentDto classTeacherComment = classTeacherCommentService.getClassTeacherCommentById(id);
        model.addAttribute("classTeacherComment", classTeacherComment);
        return "classTeacherComments/edit";
    }

    @PostMapping("/update/{id}")
    public String updateClassTeacherComment(@PathVariable Long id,
                                @Valid @ModelAttribute("classTeacherComment") ClassTeacherCommentDto classTeacherComment, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "classTeacherComments/edit";
        }
        classTeacherComment.setId(id);
        classTeacherCommentService.saveOrUpdateClassTeacherComment(classTeacherComment);
        return "redirect:/classTeacherComments";
    }

    @GetMapping("/delete/{id}")
    public String deleteClassTeacherComment(@PathVariable Long id) {
        classTeacherCommentService.deleteClassTeacherComment(id);
        return "redirect:/classTeacherComments";
    }
}
