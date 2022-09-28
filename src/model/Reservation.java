package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        String pattern = "EEE MMM d yyyy";
        String checkIn = new SimpleDateFormat(pattern).format(checkInDate);
        String checkOut = new SimpleDateFormat(pattern).format(checkOutDate);
        return "Reservation\n" +
                customer.getFirstName() + " " + customer.getLastName() +
                "\nRoom: " + room.getRoomNumber() + " - " + room.getRoomType() + " bed" +
                "\nPrice: $" + room.getRoomPrice() + " price per night" +
                "\nCheckin Date: " + checkIn +
                "\nCheckout Date: " + checkOut;
    }

}
