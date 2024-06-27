package com.shuaibu.service.impl;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.dto.SectionDto;
import com.shuaibu.mapper.SectionMapper;
import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import com.shuaibu.service.SectionService;
import com.shuaibu.service.TermService;
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
    private final SchoolClassRepository schoolClassRepository;
    private final SportHouseRepository sportHouseRepository;

    public StudentImpl(StudentRepository studentRepository, TermRepository termRepository, SchoolClassRepository schoolClassRepository, SportHouseRepository sportHouseRepository) {
        this.studentRepository = studentRepository;
        this.termRepository = termRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.sportHouseRepository = sportHouseRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentModel> students = studentRepository.findAll();
        return students.stream().map(student -> mapToDto(student)).collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long id) {
        return mapToDto(studentRepository.findById(id).get());
    }

    @Override
    public StudentModel saveOrUpdateStudent(StudentDto studentDto) {
        // for regNo purpose
        boolean isNew = studentDto.getId() == null;

        SchoolClassModel schoolClassModel = schoolClassRepository.findById(Long.parseLong(studentDto.getStudentClassId()))
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + studentDto.getStudentClassId()));

        TermModel termModel = termRepository.findById(Long.parseLong(studentDto.getTermId()))
                .orElseThrow(() -> new EntityNotFoundException("Term not found with ID: " + studentDto.getTermId()));

        SportHouseModel sportHouseModel = sportHouseRepository.findById(Long.parseLong(studentDto.getSportHouseId()))
                .orElseThrow(() -> new EntityNotFoundException("Sport House not found with ID: " + studentDto.getSportHouseId()));


        if (isNew) {
            // Generate the registration number only for new students
            String regNo = generateStudentRegNumber(studentDto.getSectionId(), schoolClassModel.getSectionId(), studentDto.getAdmissionDate());
            studentDto.setRegNo(regNo);
        } else {
            StudentModel existingStudent = studentRepository.findById(studentDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentDto.getId()));
            studentDto.setRegNo(existingStudent.getRegNo());
        }



        studentDto.setStudentClassId(schoolClassModel.getClassName());
        studentDto.setTermId(termModel.getTermName());
        studentDto.setSportHouseId(sportHouseModel.getSportHouseName());

        return studentRepository.save(mapToModel(studentDto));
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
