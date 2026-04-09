package com.yaswanth.Alumni_Bridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.Profile;
import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repo;

    public Profile getProfile(User user) {
        return repo.findByUser(user);
    }

    public Profile saveProfile(Profile profile) {
        return repo.save(profile);
    }
}