package com.example.demo;

public class RegisterRequest {
    private String name;
    private String email;
    private String registrationType;
    private Integer seat;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRegistrationType() { return registrationType; }
    public void setRegistrationType(String registrationType) { this.registrationType = registrationType; }
    public Integer getSeat() { return seat; }
    public void setSeat(Integer seat) { this.seat = seat; }
}
