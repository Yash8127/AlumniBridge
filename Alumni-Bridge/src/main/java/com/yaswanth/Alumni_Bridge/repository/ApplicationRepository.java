package com.yaswanth.Alumni_Bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yaswanth.Alumni_Bridge.entity.Application;
import com.yaswanth.Alumni_Bridge.entity.Job;
import com.yaswanth.Alumni_Bridge.entity.User;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByUserAndJob(User user, Job job);

    List<Application> findByUser(User user);
}