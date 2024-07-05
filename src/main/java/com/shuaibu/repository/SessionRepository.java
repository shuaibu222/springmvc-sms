package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.SessionModel;

@Repository
public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    SessionModel findBySessionName(String sessionName);
}

