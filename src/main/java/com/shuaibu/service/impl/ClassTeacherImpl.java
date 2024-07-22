package com.shuaibu.service.impl;

import com.shuaibu.dto.ClassTeacherDto;
import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.dto.StaffDto;
import com.shuaibu.mapper.ClassTeacherMapper;
import com.shuaibu.mapper.SchoolClassMapper;
import com.shuaibu.mapper.StaffMapper;
import com.shuaibu.model.ClassTeacherModel;
import com.shuaibu.repository.ClassTeacherCommentRepository;
import com.shuaibu.repository.ClassTeacherRepository;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.service.ClassTeacherService;
import com.shuaibu.service.SchoolClassService;
import com.shuaibu.service.StaffService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ClassTeacherMapper.mapToDto;
import static com.shuaibu.mapper.ClassTeacherMapper.mapToModel;

@Service
public class ClassTeacherImpl implements ClassTeacherService {

    private final StaffService staffService;
    private final SchoolClassService schoolClassService;
    private final StaffRepository staffRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ClassTeacherRepository classTeacherRepository;
    private final ClassTeacherCommentRepository classTeacherCommentRepository;

    public ClassTeacherImpl(StaffService staffService, SchoolClassService schoolClassService, StaffRepository staffRepository, SchoolClassRepository schoolClassRepository, com.shuaibu.repository.ClassTeacherRepository classTeacherRepository, ClassTeacherCommentRepository classTeacherCommentRepository) {
        this.staffService = staffService;
        this.schoolClassService = schoolClassService;
        this.staffRepository = staffRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.classTeacherRepository = classTeacherRepository;
        this.classTeacherCommentRepository = classTeacherCommentRepository;
    }

    @Override
    public List<ClassTeacherDto> getAllClassTeachers() {
        List<ClassTeacherModel> ClassTeachers = classTeacherRepository.findAll();
        return ClassTeachers.stream().map(ClassTeacherMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ClassTeacherDto getClassTeacherById(Long id) {
        return mapToDto(classTeacherRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateClassTeacher(ClassTeacherDto classTeacherDto) {
        boolean isNew = classTeacherDto.getId() == null;

        // Fetch the staff and class details based on IDs
        StaffDto staffDto = staffService.getStaffById(Long.valueOf(classTeacherDto.getTeacherId()));
        SchoolClassDto schoolClassDto = schoolClassService.getSchoolClassById(Long.valueOf(classTeacherDto.getClassId()));

        if (!isNew) {
            // Clear previous links
            ClassTeacherModel existingClassTeacher = classTeacherRepository.findById(classTeacherDto.getId()).orElseThrow();
            StaffDto existingStaffDto = staffService.getStaffById(Long.valueOf(existingClassTeacher.getTeacherId()));
            SchoolClassDto existingSchoolClassDto = schoolClassService.getSchoolClassById(Long.valueOf(existingClassTeacher.getClassId()));

            existingSchoolClassDto.setClassTeacher(null);
            schoolClassRepository.save(SchoolClassMapper.mapToModel(existingSchoolClassDto));

            existingStaffDto.setClassTeacherOfId(null);
            staffRepository.save(StaffMapper.mapToModel(existingStaffDto));

            // delete all comments for that teacher
            classTeacherCommentRepository.deleteClassTeacherCommentModelByTeacherId(existingClassTeacher.getTeacherId());
        }

        // Link the class with the teacher ID as class teacher
        schoolClassDto.setClassTeacher(String.valueOf(staffDto.getId()));
        schoolClassRepository.save(SchoolClassMapper.mapToModel(schoolClassDto));

        // Link the teacher with the class ID as class teacher
        staffDto.setClassTeacherOfId(String.valueOf(schoolClassDto.getId()));
        staffRepository.save(StaffMapper.mapToModel(staffDto));

        // Set the teacher name and class name in the DTO
        classTeacherDto.setTeacherName(staffDto.getFirstName() + " " + staffDto.getLastName());
        classTeacherDto.setClassName(schoolClassDto.getSectionId() + " " + schoolClassDto.getClassName());

        // Save the class teacher model
        classTeacherRepository.save(mapToModel(classTeacherDto));
    }



    @Override
    public void deleteClassTeacher(Long id) {
        ClassTeacherModel classTeacher = classTeacherRepository.findById(id).orElseThrow();
        // delete all comments for that teacher
        classTeacherCommentRepository.deleteClassTeacherCommentModelByTeacherId(classTeacher.getTeacherId());

        classTeacherRepository.deleteById(id);
    }
}
