package com.demo.model;

public class HotelRoom {
    private int id;
    private String type;
    private String hotel;
    private double price;

    public HotelRoom(int id, String type, String hotel, double price) {
        this.id = id;
        this.type = type;
        this.hotel = hotel;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
