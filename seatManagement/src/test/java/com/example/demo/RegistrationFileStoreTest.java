package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class RegistrationFileStoreTest {

    @Test
    void saveRegistrationWritesEnrollmentToDisk(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("enrollments.json");
        RegistrationFileStore store = new RegistrationFileStore(file);

        Attendee attendee = new Attendee(
                "attendee-1",
                "Alice Example",
                "alice@example.com",
                RegistrationType.STANDARD,
                LocalDateTime.now()
        );
        attendee.setStatus(Attendee.Status.CONFIRMED);
        attendee.setSeatNumber(7);

        store.saveRegistration("tech-conference", attendee);

        List<RegistrationFileStore.RegistrationRecord> saved = store.loadRegistrations();
        assertEquals(1, saved.size());
        assertEquals("tech-conference", saved.get(0).getEventId());
        assertEquals("Alice Example", saved.get(0).getName());
        assertEquals("CONFIRMED", saved.get(0).getStatus());
        assertEquals(7, saved.get(0).getSeat());
        assertFalse(saved.get(0).getId().isBlank());
    }
}
