package com.example.demo;

public enum RegistrationType {
    VIP(0),
    STANDARD(1),
    STUDENT(2);

    private final int priorityRank;

    RegistrationType(int priorityRank) {
        this.priorityRank = priorityRank;
    }

    public int getPriorityRank() {
        return priorityRank;
    }
}
