package com.example.demo;

public class EventSummary {
    private String id;
    private String name;
    private String date;
    private String description;
    private int totalSeats;
    private int seatsRemaining;

    public EventSummary(String id, String name, String date, String description, int totalSeats, int seatsRemaining) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.totalSeats = totalSeats;
        this.seatsRemaining = seatsRemaining;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public int getTotalSeats() { return totalSeats; }
    public int getSeatsRemaining() { return seatsRemaining; }
}
