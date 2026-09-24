package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistrationFileStore {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final Path filePath;

    public RegistrationFileStore() {
        this(Path.of(System.getProperty("user.dir"), "data", "enrollments.json"));
    }

    public RegistrationFileStore(Path filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    public synchronized void saveRegistration(String eventId, Attendee attendee) {
        List<RegistrationRecord> records = loadRegistrations();
        records.add(new RegistrationRecord(
                attendee.getId(),
                eventId,
                attendee.getName(),
                attendee.getEmail(),
                attendee.getType().name(),
                attendee.getStatus().name(),
                attendee.getSeatNumber(),
                attendee.getRegisteredAt()
        ));
        writeRecords(records);
    }

    public synchronized List<RegistrationRecord> loadRegistrations() {
        if (Files.notExists(filePath)) {
            return new ArrayList<>();
        }

        try {
            String contents = Files.readString(filePath);
            if (contents == null || contents.isBlank() || contents.trim().equals("[]")) {
                return new ArrayList<>();
            }
            List<RegistrationRecord> records = MAPPER.readValue(contents, new TypeReference<>() {
            });
            return records == null ? new ArrayList<>() : records;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read registrations from " + filePath, e);
        }
    }

    private void writeRecords(List<RegistrationRecord> records) {
        try {
            Files.createDirectories(filePath.getParent());
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), records);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write registrations to " + filePath, e);
        }
    }

    private void ensureFileExists() {
        try {
            Files.createDirectories(filePath.getParent());
            if (Files.notExists(filePath)) {
                Files.writeString(filePath, "[]");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize registrations file at " + filePath, e);
        }
    }

    public static class RegistrationRecord {
        private String id;
        private String eventId;
        private String name;
        private String email;
        private String registrationType;
        private String status;
        private Integer seat;
        private LocalDateTime registeredAt;

        public RegistrationRecord() {
        }

        public RegistrationRecord(
                String id,
                String eventId,
                String name,
                String email,
                String registrationType,
                String status,
                Integer seat,
                LocalDateTime registeredAt
        ) {
            this.id = id;
            this.eventId = eventId;
            this.name = name;
            this.email = email;
            this.registrationType = registrationType;
            this.status = status;
            this.seat = seat;
            this.registeredAt = registeredAt;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getEventId() { return eventId; }
        public void setEventId(String eventId) { this.eventId = eventId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getRegistrationType() { return registrationType; }
        public void setRegistrationType(String registrationType) { this.registrationType = registrationType; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Integer getSeat() { return seat; }
        public void setSeat(Integer seat) { this.seat = seat; }

        public LocalDateTime getRegisteredAt() { return registeredAt; }
        public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    }
}
