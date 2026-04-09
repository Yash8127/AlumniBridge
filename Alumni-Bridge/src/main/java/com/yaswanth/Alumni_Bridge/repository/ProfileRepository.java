package com.yaswanth.Alumni_Bridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yaswanth.Alumni_Bridge.entity.Profile;
import com.yaswanth.Alumni_Bridge.entity.User;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);
}