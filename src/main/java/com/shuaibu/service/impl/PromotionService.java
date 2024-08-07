package com.shuaibu.service.impl;

import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {
    private final StudentRepository studentRepository;
    private final SchoolClassRepository classRepository;
    private final SectionRepository sectionRepository;
    private final WalletRepository walletRepository;

    public PromotionService(StudentRepository studentRepository, SchoolClassRepository classRepository, SectionRepository sectionRepository, WalletRepository walletRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.sectionRepository = sectionRepository;
        this.walletRepository = walletRepository;
    }

    public void promoteStudents(List<Long> studentIds, Long toClassId) {
        SchoolClassModel toClass = classRepository.findById(toClassId).orElseThrow();
        SectionModel toSection = sectionRepository.findBySectionName(toClass.getSectionId());

        List<StudentModel> students = studentRepository.findAllById(studentIds);

        for (StudentModel student : students) {
            if (student.getStudentClassName().equalsIgnoreCase("sss three") ||
                    student.getStudentClassName().equalsIgnoreCase("sss 3")) {
                student.setStudentClassId(null);
                student.setStudentClassName("Graduated");
                student.setSectionId(null);
                student.setSectionName("Graduated");
                student.setIsActive("False");
                studentRepository.save(student);
            } else {
                // updating his wallet class reference
                WalletModel walletModel = walletRepository.findByRegNo(student.getRegNo());
                walletModel.setStudentClass(toClass.getClassName());
                walletRepository.save(walletModel);

                student.setStudentClassId(String.valueOf(toClass.getId()));
                student.setStudentClassName(toClass.getClassName());
                student.setSectionId(String.valueOf(toSection.getId()));
                student.setSectionName(toSection.getSectionName());
                studentRepository.save(student);
            }
        }
    }
}
