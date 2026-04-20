package com.yaswanth.Alumni_Bridge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    // getters & setters
}