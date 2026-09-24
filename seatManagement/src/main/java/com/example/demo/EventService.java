package com.example.demo;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final Map<String, Event> events = new LinkedHashMap<>();
    private final Map<String, WaitlistManager> waitlists = new LinkedHashMap<>();

    public EventService() {
        addEvent("tech-conference", "Tech Conference", "Oct 14, 2026", "Talks and workshops on where software is headed next.", 50);
        addEvent("music-night", "Music Night", "Oct 21, 2026", "A live evening of local bands and acoustic sets.", 30);
        addEvent("sports-meet", "Sports Meet", "Nov 2, 2026", "Inter-college track and field finals.", 60);
    }

    private void addEvent(String id, String name, String date, String description, int totalSeats) {
        Event event = new Event(id, name, date, description, totalSeats);
        events.put(id, event);
        waitlists.put(id, new WaitlistManager(event));
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public Event getEvent(String id) {
        return events.get(id);
    }

    public WaitlistManager getWaitlistManager(String id) {
        return waitlists.get(id);
    }

    public Attendee registerAttendee(String eventId, String name, String email, String registrationTypeRaw, Integer seat) {
        WaitlistManager manager = waitlists.get(eventId);
        if (manager == null) {
            throw new IllegalArgumentException("Unknown event: " + eventId);
        }

        RegistrationType type = RegistrationType.valueOf(registrationTypeRaw.toUpperCase());
        Attendee attendee = new Attendee(UUID.randomUUID().toString(), name, email, type, LocalDateTime.now());
        return manager.register(attendee, seat);
    }
}
