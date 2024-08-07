package com.shuaibu.repository;

import com.shuaibu.model.HeadTeacherCommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadTeacherCommentRepository extends JpaRepository<HeadTeacherCommentModel, Long> {
    void deleteByTeacherId(String teacherId);
}

