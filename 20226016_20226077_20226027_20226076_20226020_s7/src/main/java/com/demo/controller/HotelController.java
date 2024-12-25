package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.HotelRoom;
import com.demo.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/available")
    public ResponseEntity<ResponseEntity<List<HotelRoom>>> getAvailableHotels() {
        return ResponseEntity.ok(hotelService.getAllRooms());
    }

    @PostMapping("/book")
    public ResponseEntity<Map<String, String>> bookHotel(@RequestBody Map<String, String> bookingDetails) {
        String hotelName = bookingDetails.get("hotelName");
        String guestName = bookingDetails.get("guestName");
        return ResponseEntity.ok(
                Map.of(
                        "message", "Hotel booked successfully",
                        "hotelName", hotelName,
                        "guestName", guestName
                )
        );
    }
}
