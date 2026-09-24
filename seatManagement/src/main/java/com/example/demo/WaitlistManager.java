package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class WaitlistManager {

    private static final Comparator<Attendee> WAITLIST_ORDER =
            Comparator.comparingInt((Attendee a) -> a.getType().getPriorityRank())
                      .thenComparing(Attendee::getRegisteredAt);

    private final PriorityQueue<Attendee> heap = new PriorityQueue<>(WAITLIST_ORDER);
    private final List<Attendee> confirmed = new ArrayList<>();
    private final List<Attendee> waitlisted = new ArrayList<>();
    private final Event event;

    public WaitlistManager(Event event) {
        this.event = event;
    }

    public Attendee register(Attendee attendee, Integer requestedSeat) {
        if (event.hasSeatAvailable()) {
            Integer seat = (requestedSeat != null && !event.isSeatTaken(requestedSeat))
                    ? requestedSeat
                    : event.nextFreeSeat();

            event.occupySeat(seat);
            attendee.setSeatNumber(seat);
            attendee.setStatus(Attendee.Status.CONFIRMED);
            confirmed.add(attendee);
            return attendee;
        }

        heap.offer(attendee);
        attendee.setStatus(Attendee.Status.WAITLISTED);
        waitlisted.add(attendee);
        return attendee;
    }

    public boolean cancel(String attendeeId) {
        Attendee cancelling = confirmed.stream()
                .filter(a -> a.getId().equals(attendeeId))
                .findFirst()
                .orElse(null);

        if (cancelling == null) {
            return false;
        }

        confirmed.remove(cancelling);
        event.freeSeat(cancelling.getSeatNumber());
        promoteNextWaitlisted();
        return true;
    }

    private void promoteNextWaitlisted() {
        if (heap.isEmpty()) {
            return;
        }

        Attendee promoted = heap.poll();
        waitlisted.remove(promoted);

        Integer seat = event.nextFreeSeat();
        event.occupySeat(seat);
        promoted.setSeatNumber(seat);
        promoted.setStatus(Attendee.Status.CONFIRMED);
        confirmed.add(promoted);
    }

    public List<Attendee> getConfirmed() { return confirmed; }
    public List<Attendee> getWaitlisted() { return waitlisted; }
}
