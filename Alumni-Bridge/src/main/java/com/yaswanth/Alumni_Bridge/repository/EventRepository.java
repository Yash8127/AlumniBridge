package com.yaswanth.Alumni_Bridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.Event;

@Service
public interface EventRepository extends JpaRepository<Event, Long> {
}