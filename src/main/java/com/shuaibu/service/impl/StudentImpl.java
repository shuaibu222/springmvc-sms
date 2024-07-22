package com.shuaibu.service.impl;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.mapper.StudentMapper;
import com.shuaibu.mapper.UserMapper;
import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import com.shuaibu.service.StudentService;
import com.shuaibu.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final UserRepository userRepository;
    private final StudentIdCounterRepository studentIdCounterRepository;
    private final WalletRepository walletRepository;

    public StudentImpl(PasswordEncoder passwordEncoder, StudentRepository studentRepository, TermRepository termRepository, SectionRepository sectionRepository, SportHouseRepository sportHouseRepository, SchoolClassRepository schoolClassRepository, UserService userService, UserRepository userRepository, StudentIdCounterRepository studentIdCounterRepository, WalletRepository walletRepository) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.termRepository = termRepository;
        this.sectionRepository = sectionRepository;
        this.sportHouseRepository = sportHouseRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.studentIdCounterRepository = studentIdCounterRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentModel> students = studentRepository.findAll();
        return students.stream()
                .filter(s -> s.getIsActive().equals("True"))
                .map(StudentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> getStudentsByClassId(Long classId) {
        List<StudentModel> students = studentRepository.findAll();
        return students.stream()
                .filter(s -> s.getIsActive().equals("True") && Objects.equals(s.getStudentClassId(), String.valueOf(classId)))
                .map(StudentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long id) {
        return mapToDto(studentRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateStudent(StudentDto studentDto) {
        boolean isNew = studentDto.getId() == null;

        SectionModel sectionModel = sectionRepository.findById(Long.parseLong(studentDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + studentDto.getSectionId()));

        SchoolClassModel classModel = schoolClassRepository.findById(Long.parseLong(studentDto.getStudentClassId()))
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + studentDto.getStudentClassId()));

        TermModel termModel = termRepository.findById(Long.parseLong(studentDto.getTermId()))
                .orElseThrow(() -> new EntityNotFoundException("Term not found with ID: " + studentDto.getTermId()));

        SportHouseModel sportHouseModel = sportHouseRepository.findById(Long.parseLong(studentDto.getSportHouseId()))
                .orElseThrow(() -> new EntityNotFoundException("Sport House not found with ID: " + studentDto.getSportHouseId()));

        // for the purpose of generating regNo and create or updating user account for login purpose
        if (isNew) {
            studentDto.setRegNo(generateYearlyId());

            newUserModel(studentDto);

            StudentModel studentFromRepo = studentRepository.findByUserName(studentDto.getUserName());
            if (studentFromRepo != null) {
                throw new IllegalArgumentException("Student exists");
            }
        } else {
            StudentModel existingStudent = studentRepository.findById(studentDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentDto.getId()));
            studentDto.setRegNo(existingStudent.getRegNo());

            if (existingStudent.getUserId() != null) {
                UserModel userModel = new UserModel();
                userModel.setId(existingStudent.getUserId());
                userModel.setUsername(studentDto.getUserName());
                userModel.setPassword(studentDto.getPassword());
                userModel.setIsActive(studentDto.getIsActive());
                userModel.setRoles(Collections.singleton("ROLE_STUDENT"));

                UserModel savedUser = userService.saveUser(UserMapper.mapToDto(userModel));
                studentDto.setUserId(savedUser.getId());
                studentDto.setPassword(passwordEncoder.encode(studentDto.getPassword()));
            }
        }

        studentDto.setSectionName(sectionModel.getSectionName());
        studentDto.setStudentClassName(classModel.getClassName());
        studentDto.setTermId(termModel.getTermName());
        studentDto.setSportHouseId(sportHouseModel.getSportHouseName());

        StudentModel createdStudent = studentRepository.save(mapToModel(studentDto));
        WalletModel walletModel = WalletModel.builder()
                .isActive(createdStudent.getIsActive())
                .studentId(createdStudent.getId())
                .regNo(createdStudent.getRegNo())
                .balance(createdStudent.getBalance())
                .build();
        walletRepository.save(walletModel);
    }

    @Override
    public void deleteStudent(Long id) {
        StudentModel studentModel = studentRepository.findById(id).orElseThrow();
        userRepository.deleteById(studentModel.getUserId());
        studentRepository.deleteById(id);
    }

    private void newUserModel(StudentDto studentDto) {
        UserModel userModel = new UserModel();
        userModel.setUsername(studentDto.getUserName());
        userModel.setPassword(studentDto.getPassword());
        userModel.setIsActive(studentDto.getIsActive());
        userModel.setRoles(Collections.singleton("ROLE_STUDENT"));

        UserModel savedUser = userService.saveUser(UserMapper.mapToDto(userModel));
        studentDto.setUserId(savedUser.getId());
        studentDto.setPassword(passwordEncoder.encode(studentDto.getPassword()));
    }

    private synchronized String generateYearlyId() {
        int currentYear = Year.now().getValue();
        StudentIdCounter idCounter = studentIdCounterRepository.findByAcademicYear(currentYear);

        if (idCounter == null) {
            idCounter = new StudentIdCounter();
            idCounter.setAcademicYear(currentYear);
            idCounter.setLastId(1000L);
        }

        Long newId = idCounter.getLastId() + 1;
        idCounter.setLastId(newId);
        studentIdCounterRepository.save(idCounter);

        return String.format("%d-%04d", currentYear, newId);
    }
}
