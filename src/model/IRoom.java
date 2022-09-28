package model;

public interface IRoom {

    public String getRoomNumber();

    public Double getRoomPrice();

    public RoomType getRoomType();

    public boolean isFree();

    default String cleanRoom() {
        return "The room is clean";
    }
}
