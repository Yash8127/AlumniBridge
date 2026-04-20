package com.yaswanth.Alumni_Bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.Application;
import com.yaswanth.Alumni_Bridge.entity.Job;
import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.repository.ApplicationRepository;

@Service
public class ApplicationService {

	@Autowired
	private ApplicationRepository repo;

	public Application apply(Application app) {
		return repo.save(app);
	}

	public boolean alreadyApplied(User user, Job job) {
		return repo.existsByUserAndJob(user, job);
	}

	public List<Application> getUserApplications(User user) {
		return repo.findByUser(user);
	}
	public List<Application> getApplicationsByUser(User user) {
	    return repo.findByUser(user);
	}
}