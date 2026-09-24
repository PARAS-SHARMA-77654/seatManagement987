package com.example.demo;

import java.util.Set;

public class SeatsResponse {
    private int totalSeats;
    private Set<Integer> takenSeats;

    public SeatsResponse(int totalSeats, Set<Integer> takenSeats) {
        this.totalSeats = totalSeats;
        this.takenSeats = takenSeats;
    }

    public int getTotalSeats() { return totalSeats; }
    public Set<Integer> getTakenSeats() { return takenSeats; }
}
