package com.yaswanth.Alumni_Bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yaswanth.Alumni_Bridge.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findAll();
}