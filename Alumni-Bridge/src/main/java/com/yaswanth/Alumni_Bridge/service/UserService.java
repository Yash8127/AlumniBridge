package com.yaswanth.Alumni_Bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	// REGISTER
	public User registerUser(User user) {
		return repo.save(user);
	}

	// LOGIN (temporary - will be removed when Spring Security is used)
	public User loginUser(String email, String password) {
		User user = repo.findByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			return user;
		}

		return null;
	}

	// 🔥 IMPORTANT FOR PROFILE PHASE
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public List<User> getAllUsers() {
		return repo.findAll();
	}

	public List<User> searchUsers(String keyword, String role) {

		if ((keyword == null || keyword.isEmpty()) && (role == null || role.isEmpty())) {
			return repo.findAll();
		}

		if (keyword != null && !keyword.isEmpty() && role != null && !role.isEmpty()) {
			return repo.findByNameContainingIgnoreCaseAndRole(keyword, role);
		}

		if (keyword != null && !keyword.isEmpty()) {
			return repo.findByNameContainingIgnoreCase(keyword);
		}

		if (role != null && !role.isEmpty()) {
			return repo.findByRole(role);
		}

		return repo.findAll();
	}

	public User getUserById(Long id) {
		return repo.findById(id).orElse(null);
	}
}