package com.yaswanth.Alumni_Bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.Job;
import com.yaswanth.Alumni_Bridge.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository repo;

    public Job saveJob(Job job) {
        return repo.save(job);
    }

    public List<Job> getAllJobs() {
        return repo.findAll();
    }

    public Job getJobById(Long id) {
        return repo.findById(id).orElse(null);
    }
}