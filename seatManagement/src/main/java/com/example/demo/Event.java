package com.example.demo;

import java.util.HashSet;
import java.util.Set;

public class Event {

    private final String id;
    private final String name;
    private final String date;
    private final String description;
    private final int totalSeats;
    private final Set<Integer> takenSeats = new HashSet<>();

    public Event(String id, String name, String date, String description, int totalSeats) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.totalSeats = totalSeats;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public int getTotalSeats() { return totalSeats; }
    public int getSeatsFilled() { return takenSeats.size(); }
    public int getSeatsRemaining() { return totalSeats - takenSeats.size(); }
    public Set<Integer> getTakenSeats() { return takenSeats; }

    public boolean hasSeatAvailable() {
        return takenSeats.size() < totalSeats;
    }

    public boolean isSeatTaken(int seatNumber) {
        return takenSeats.contains(seatNumber);
    }

    public void occupySeat(int seatNumber) {
        if (!hasSeatAvailable()) {
            throw new IllegalStateException("No seats remaining for event: " + name);
        }
        takenSeats.add(seatNumber);
    }

    public void freeSeat(int seatNumber) {
        takenSeats.remove(seatNumber);
    }

    public Integer nextFreeSeat() {
        for (int i = 1; i <= totalSeats; i++) {
            if (!takenSeats.contains(i)) {
                return i;
            }
        }
        return null;
    }
}
