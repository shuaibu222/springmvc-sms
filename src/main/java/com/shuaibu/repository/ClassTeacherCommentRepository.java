package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ClassTeacherCommentModel;

@Repository
public interface ClassTeacherCommentRepository extends JpaRepository<ClassTeacherCommentModel, Long> {
    void findByTeacherId(String classTeacher);

    void deleteByTeacherId(String teacherId);
}

