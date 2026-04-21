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

	public List<User> searchUsers(String keyword, String role, Long currentUserId) {

		List<User> users;

		if ((keyword == null || keyword.isEmpty()) && (role == null || role.isEmpty())) {
			users = repo.findAll();
		} else if (keyword != null && !keyword.isEmpty() && role != null && !role.isEmpty()) {
			users = repo.findByNameContainingIgnoreCaseAndRole(keyword, role);
		} else if (keyword != null && !keyword.isEmpty()) {
			users = repo.findByNameContainingIgnoreCase(keyword);
		} else if (role != null && !role.isEmpty()) {
			users = repo.findByRole(role);
		} else {
			users = repo.findAll();
		}

		// 🔥 FILTER HERE
		return users.stream().filter(u -> !u.getId().equals(currentUserId)) // ❌ remove self
				.filter(u -> !u.getRole().equals("ADMIN")) // ❌ remove admin
				.toList();
	}

	public User getUserById(Long id) {
		return repo.findById(id).orElse(null);
	}

	public void deleteUser(Long id) {
		repo.deleteById(id);
	}

	public void toggleBlockUser(Long id) {

		User user = repo.findById(id).orElse(null);

		if (user != null) {

			Boolean blocked = user.getBlocked();

			if (blocked == null) {
				blocked = false;
			}

			user.setBlocked(!blocked);

			repo.save(user);
		}
	}

}