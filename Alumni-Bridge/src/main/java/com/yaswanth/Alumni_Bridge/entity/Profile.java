package com.yaswanth.Alumni_Bridge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String skills;
	private String company;
	private String experience;
	private String bio;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	// Getters & Setters
}