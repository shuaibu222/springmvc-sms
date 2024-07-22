package com.shuaibu.service.impl;

import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.model.SectionModel;
import com.shuaibu.model.StudentIdCounter;
import com.shuaibu.model.StudentModel;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.SectionRepository;
import com.shuaibu.repository.StudentIdCounterRepository;
import com.shuaibu.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class PromotionService {
    private final StudentRepository studentRepository;
    private final SchoolClassRepository classRepository;
    private final SectionRepository sectionRepository;

    public PromotionService(StudentRepository studentRepository, SchoolClassRepository classRepository, SectionRepository sectionRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.sectionRepository = sectionRepository;
    }

    public void promoteStudents(List<Long> studentIds, Long toClassId) {
        SchoolClassModel toClass = classRepository.findById(toClassId).orElseThrow();
        SectionModel toSection = sectionRepository.findBySectionName(toClass.getSectionId());

        List<StudentModel> students = studentRepository.findAllById(studentIds);

        for (StudentModel student : students) {
            if (student.getStudentClassName().equalsIgnoreCase("three") ||
                    student.getStudentClassName().equalsIgnoreCase("3") &&
                            student.getSectionName().equalsIgnoreCase("sss")) {
                student.setStudentClassId(null);
                student.setStudentClassName("Graduated");
                student.setSectionId(null);
                student.setSectionName("Graduated");
                student.setIsActive("False");
                studentRepository.save(student);
            } else {
                student.setStudentClassId(String.valueOf(toClass.getId()));
                student.setStudentClassName(toClass.getClassName());
                student.setSectionId(String.valueOf(toSection.getId()));
                student.setSectionName(toSection.getSectionName());
                studentRepository.save(student);
            }
        }
    }
}
