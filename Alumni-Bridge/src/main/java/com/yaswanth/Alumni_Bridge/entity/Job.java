package com.yaswanth.Alumni_Bridge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;

    // Getters & Setters
}