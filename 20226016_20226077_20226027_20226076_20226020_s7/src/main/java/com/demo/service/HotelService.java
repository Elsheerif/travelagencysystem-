package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.model.HotelRoom;

@Service
public class HotelService {

    private final List<HotelRoom> hotelRooms = new ArrayList<>();
    private final List<String> bookings = new ArrayList<>();

    public HotelService() {
        hotelRooms.add(new HotelRoom(1, "Single", "Hilton Cairo", 100));
        hotelRooms.add(new HotelRoom(2, "Double", "Marriott Cairo", 200));
        hotelRooms.add(new HotelRoom(3, "Family", "Four Seasons Cairo", 300));
    }

    public ResponseEntity<List<HotelRoom>> getAllRooms() {
        return ResponseEntity.ok(hotelRooms);
    }

    public ResponseEntity<List<HotelRoom>> searchRooms(String type) {
        List<HotelRoom> filteredRooms = hotelRooms.stream()
                .filter(room -> room.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filteredRooms);
    }

    public ResponseEntity<String> bookRoom(int roomId, int userId) {
        for (HotelRoom room : hotelRooms) {
            if (room.getId() == roomId) {
                String booking = "User ID: " + userId + " booked " + room.getType() + " room at " + room.getHotel();
                bookings.add(booking);
                return ResponseEntity.ok("Room booked successfully!");
            }
        }
        return ResponseEntity.status(404).body("Room not found!");
    }

    public List<String> getBookings() {
        return bookings;
    }
}
