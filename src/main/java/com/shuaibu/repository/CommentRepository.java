package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.CommentModel;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
}

