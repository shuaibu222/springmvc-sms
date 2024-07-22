package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.EventModel;

@Repository
public interface EventRepository extends JpaRepository<EventModel, Long> {
}

