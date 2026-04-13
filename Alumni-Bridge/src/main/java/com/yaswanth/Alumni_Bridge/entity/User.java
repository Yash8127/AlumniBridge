package com.yaswanth.Alumni_Bridge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;
	private String password;
	private String role;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Profile profile;
	private String profileImage;

	// getters & setters
}