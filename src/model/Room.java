package model;

import java.util.Objects;

public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room (String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return this.price.equals(0.0);
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +"; Type: " + enumeration + " Bed Room; Price: $" + price;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IRoom)) {
            return false;
        }
        IRoom room = (IRoom) obj;
        return room.getRoomNumber().equals(roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

}
