package com.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.model.Event;

@Service
public class EventService {

    public String bookEvent(int eventId, int userId) {
        return "Event booked successfully!";
    }

    public List<Event> getAllEvents() {
        return List.of(new Event(1, "Event A", "Location A", "2024-01-01"));
    }
}
