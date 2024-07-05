package com.shuaibu.service.impl;

import com.shuaibu.mapper.StudentMapper;
import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.StudentMapper.*;

@Service
public class StudentImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final SectionRepository sectionRepository;
    private final SportHouseRepository sportHouseRepository;
    private final SchoolClassRepository schoolClassRepository;

    public StudentImpl(StudentRepository studentRepository, TermRepository termRepository, SectionRepository sectionRepository, SportHouseRepository sportHouseRepository, SchoolClassRepository schoolClassRepository) {
        this.studentRepository = studentRepository;
        this.termRepository = termRepository;
        this.sectionRepository = sectionRepository;
        this.sportHouseRepository = sportHouseRepository;
        this.schoolClassRepository = schoolClassRepository;
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
        // for regNo purpose
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



        studentDto.setSectionId(sectionModel.getSectionName());
        studentDto.setStudentClassId(classModel.getClassName());
        studentDto.setTermId(termModel.getTermName());
        studentDto.setSportHouseId(sportHouseModel.getSportHouseName());

        studentRepository.save(mapToModel(studentDto));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public String generateStudentRegNumber(String sectionId, String sectionPart, String admissionDate) {
        // Convert sectionId to Long if necessary
        Long sectionIdLong = Long.parseLong(sectionId);

        // Get the last used number for the section
        Integer lastUsedNumber = studentRepository.findMaxRegNumberBySectionId(sectionIdLong);
        if (lastUsedNumber == null) {
            lastUsedNumber = 0;
        }

        // Increment the last used number
        Integer newNumber = lastUsedNumber + 1;

        // Format the number as 001, 002, etc.
        String formattedNumber = String.format("%03d", newNumber);

        // Generate the registration number (example format: SEC001/23)
        String year = admissionDate.substring(admissionDate.length() - 2); // Assuming admissionDate is in YYYY-MM-DD format
        return sectionPart.substring(0, 3) + "/" + year + "/" + formattedNumber;
    }
}
