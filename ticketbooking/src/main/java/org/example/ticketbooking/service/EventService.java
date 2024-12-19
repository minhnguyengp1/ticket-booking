package org.example.ticketbooking.service;

import org.example.ticketbooking.model.Event;
import org.example.ticketbooking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Tạo sự kiện mới
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Lấy sự kiện theo ID
    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // Cập nhật sự kiện
    public Event updateEvent(Long eventId, Event eventDetails) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setName(eventDetails.getName());
        event.setLocation(eventDetails.getLocation());
        event.setDate(eventDetails.getDate());
        event.setUpdatedAt(eventDetails.getUpdatedAt());

        return eventRepository.save(event);
    }

    // Xóa sự kiện
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
