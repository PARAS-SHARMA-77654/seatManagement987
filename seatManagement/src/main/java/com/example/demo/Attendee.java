package com.example.demo;

import java.time.LocalDateTime;

public class Attendee {

    public enum Status {
        WAITING,
        CONFIRMED,
        WAITLISTED
    }

    private final String id;
    private final String name;
    private final String email;
    private final RegistrationType type;
    private final LocalDateTime registeredAt;
    private Status status;
    private Integer seatNumber;

    public Attendee(String id, String name, String email, RegistrationType type, LocalDateTime registeredAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.registeredAt = registeredAt;
        this.status = Status.WAITING;
        this.seatNumber = null;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public RegistrationType getType() { return type; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

    @Override
    public String toString() {
        return String.format("Attendee{id=%s, name=%s, type=%s, status=%s, seat=%s}", id, name, type, status, seatNumber);
    }
}
