package com.yaswanth.Alumni_Bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.Event;
import com.yaswanth.Alumni_Bridge.entity.EventRegistration;
import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.repository.EventRegistrationRepository;
import com.yaswanth.Alumni_Bridge.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private EventRegistrationRepository regRepo;

	public List<Event> getAllEvents() {
		return eventRepo.findAll();
	}

	public void saveEvent(Event event) {
		eventRepo.save(event);
	}

	public void register(User user, Event event) {

		if (!regRepo.existsByUserAndEvent(user, event)) {
			EventRegistration reg = new EventRegistration();
			reg.setUser(user);
			reg.setEvent(event);
			regRepo.save(reg);
		}
	}

	public boolean isRegistered(User user, Event event) {
		return regRepo.existsByUserAndEvent(user, event);
	}

	public void deleteEvent(Long id) {
		eventRepo.deleteById(id);
	}
}
