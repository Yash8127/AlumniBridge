package com.yaswanth.Alumni_Bridge.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaswanth.Alumni_Bridge.entity.*;
import com.yaswanth.Alumni_Bridge.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository repo;

	public void sendMessage(User sender, User receiver, String content) {
		Message msg = new Message();
		msg.setSender(sender);
		msg.setReceiver(receiver);
		msg.setContent(content);
		msg.setTimestamp(LocalDateTime.now());
		msg.setSeen(false); // default

		repo.save(msg);
	}

	public List<Message> getInbox(User user) {
		return repo.findByReceiver(user);
	}

	public List<Message> getConversation(User u1, User u2) {
		return repo.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(u1, u2, u1, u2);
	}

	public void markAsSeen(User current, User other) {

		List<Message> messages = repo.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(other, current,
				other, current);

		for (Message m : messages) {
			if (m.getReceiver().getId().equals(current.getId())) {
				m.setSeen(true);
			}
		}

		repo.saveAll(messages);
	}
}