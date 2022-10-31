package com.example.payment;

public class TicketModel {
    public String id;
    public String movieName;
    public  String ticketType;
    public String numberOfSeats;
    public  String seatNumbers;
    public double totalAmount;
    public String fullName;
    public String address;
    public  String email;
    public boolean status;
    public TicketModel(String id, String movieName, String ticketType, String numberOfSeats, String seatNumbers, double totalAmount, String fullName, String address, String email,boolean status) {
        this.id = id;
        this.movieName = movieName;
        this.ticketType = ticketType;
        this.numberOfSeats = numberOfSeats;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.status = status;
    }
}
