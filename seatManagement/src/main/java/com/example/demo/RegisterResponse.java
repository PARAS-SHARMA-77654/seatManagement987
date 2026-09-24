package com.example.demo;

public class RegisterResponse {
    private String status;
    private Integer seat;

    public RegisterResponse(String status, Integer seat) {
        this.status = status;
        this.seat = seat;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getSeat() { return seat; }
    public void setSeat(Integer seat) { this.seat = seat; }
}
