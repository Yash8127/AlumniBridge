package com.yaswanth.Alumni_Bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yaswanth.Alumni_Bridge.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	List<User> findByNameContainingIgnoreCase(String name);

	List<User> findByRole(String role);

	List<User> findByNameContainingIgnoreCaseAndRole(String name, String role);

	List<User> findAll();

	
}