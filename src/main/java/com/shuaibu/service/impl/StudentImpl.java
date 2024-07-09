package com.shuaibu.service.impl;

import com.shuaibu.mapper.StudentMapper;
import com.shuaibu.mapper.UserMapper;
import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import com.shuaibu.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.StudentMapper.mapToModel;
import static com.shuaibu.mapper.StudentMapper.*;

@Service
public class StudentImpl implements StudentService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final SectionRepository sectionRepository;
    private final SportHouseRepository sportHouseRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserService userService;
    private final RegNoRepository regNoRepository;
    private final UserRepository userRepository;

    public StudentImpl(PasswordEncoder passwordEncoder, StudentRepository studentRepository, TermRepository termRepository, SectionRepository sectionRepository, SportHouseRepository sportHouseRepository, SchoolClassRepository schoolClassRepository, UserService userService, RegNoRepository regNoRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.termRepository = termRepository;
        this.sectionRepository = sectionRepository;
        this.sportHouseRepository = sportHouseRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.userService = userService;
        this.regNoRepository = regNoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentModel> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long id) {
        return mapToDto(studentRepository.findById(id).get());
    }

    @Override
    public void saveOrUpdateStudent(StudentDto studentDto) {
        // For regNo purpose
        boolean isNew = studentDto.getId() == null;

        SectionModel sectionModel = sectionRepository.findById(Long.parseLong(studentDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + studentDto.getSectionId()));

        SchoolClassModel classModel = schoolClassRepository.findById(Long.parseLong(studentDto.getStudentClassId()))
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + studentDto.getStudentClassId()));

        TermModel termModel = termRepository.findById(Long.parseLong(studentDto.getTermId()))
                .orElseThrow(() -> new EntityNotFoundException("Term not found with ID: " + studentDto.getTermId()));

        SportHouseModel sportHouseModel = sportHouseRepository.findById(Long.parseLong(studentDto.getSportHouseId()))
                .orElseThrow(() -> new EntityNotFoundException("Sport House not found with ID: " + studentDto.getSportHouseId()));

        if (isNew) {
            // Generate the registration number only for new students
            String regNo = generateStudentRegNumber(studentDto.getSectionId(), sectionModel.getSectionName(), studentDto.getAdmissionDate());
            studentDto.setRegNo(regNo);
        } else {
            StudentModel existingStudent = studentRepository.findById(studentDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentDto.getId()));
            studentDto.setRegNo(existingStudent.getRegNo());
        }

        StudentModel existingStudent;
        if (studentDto.getId() != null) {
            existingStudent = studentRepository.findById(studentDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentDto.getId()));

            // Delete user associated with the student (if it exists)
            if (existingStudent.getUserId() != null) {
                UserModel userModel = new UserModel();
                userModel.setId(existingStudent.getUserId());
                userModel.setUsername(studentDto.getUserName());
                userModel.setPassword(studentDto.getPassword());
                userModel.setRoles(Collections.singleton("ROLE_STUDENT"));

                UserModel savedUser = userService.saveUser(UserMapper.mapToDto(userModel));
                studentDto.setUserId(savedUser.getId());
                studentDto.setPassword(passwordEncoder.encode(studentDto.getPassword()));
            }

        } else {
            newUserModel(studentDto);
        }

        studentDto.setSectionId(sectionModel.getSectionName());
        studentDto.setStudentClassId(classModel.getClassName());
        studentDto.setTermId(termModel.getTermName());
        studentDto.setSportHouseId(sportHouseModel.getSportHouseName());

        studentRepository.save(mapToModel(studentDto));
    }


    @Override
    public void deleteStudent(Long id) {
        StudentModel studentModel = studentRepository.findById(id).orElseThrow();
        userRepository.deleteById(studentModel.getUserId());

        studentRepository.deleteById(id);
    }

    // UTILITY FUNCTIONS
    private void newUserModel(StudentDto studentDto) {
        UserModel userModel = new UserModel();
        userModel.setUsername(studentDto.getUserName());
        userModel.setPassword(studentDto.getPassword());
        userModel.setRoles(Collections.singleton("ROLE_STUDENT"));

        UserModel savedUser = userService.saveUser(UserMapper.mapToDto(userModel));
        studentDto.setUserId(savedUser.getId());
        studentDto.setPassword(passwordEncoder.encode(studentDto.getPassword()));
    }

    public String generateStudentRegNumber(String sectionId, String sectionPart, String admissionDate) {
        Long sectionIdLong = Long.parseLong(sectionId);

        Integer lastUsedNumber = regNoRepository.findMaxRegNumberBySectionId(sectionIdLong);
        if (lastUsedNumber == null) {
            lastUsedNumber = 0;
        }

        Integer newNumber = lastUsedNumber + 1;
        String formattedNumber = String.format("%03d", newNumber);

        String year = admissionDate.substring(admissionDate.length() - 2);
        return sectionPart.substring(0, 3) + "/" + year + "/" + formattedNumber;
    }
}
