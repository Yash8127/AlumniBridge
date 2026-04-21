package com.yaswanth.Alumni_Bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.Event;

@Service
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findAll();
}