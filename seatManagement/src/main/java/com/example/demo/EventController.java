package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventSummary> getAllEvents() {
        return eventService.getEvents().values().stream()
                .map(e -> new EventSummary(e.getId(), e.getName(), e.getDate(), e.getDescription(), e.getTotalSeats(), e.getSeatsRemaining()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<SeatsResponse> getSeats(@PathVariable String id) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new SeatsResponse(event.getTotalSeats(), event.getTakenSeats()));
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<RegisterResponse> register(@PathVariable String id, @RequestBody RegisterRequest request) {
        Event event = eventService.getEvent(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        Attendee attendee = eventService.registerAttendee(
                id, request.getName(), request.getEmail(), request.getRegistrationType(), request.getSeat());

        return ResponseEntity.ok(new RegisterResponse(attendee.getStatus().name(), attendee.getSeatNumber()));
    }

    @GetMapping(value = "/{id}/manifest", produces = "text/csv")
    public ResponseEntity<String> getManifest(@PathVariable String id) {
        WaitlistManager manager = eventService.getWaitlistManager(id);
        if (manager == null) {
            return ResponseEntity.notFound().build();
        }

        String csv = ManifestExporter.buildCsv(manager.getConfirmed(), manager.getWaitlisted());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header("Content-Disposition", "attachment; filename=\"" + id + "-manifest.csv\"")
                .body(csv);
    }
}
