package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private final Map<String, IRoom> listIRoom = new HashMap<>();
    private final Collection<Reservation> listReservation = new ArrayList<>();
    private static ReservationService  single_instance;

    public static ReservationService getInstance() {
        if (single_instance == null) {
            single_instance = new ReservationService();
        }
        return single_instance;
    }

    public void addRoom(IRoom room) {
        room.cleanRoom();
        listIRoom.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return listIRoom.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<IRoom> rooms = findRooms(checkInDate, checkOutDate);
        if (rooms != null && rooms.contains(room)) {
            Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
            listReservation.add(reservation);
            return reservation;
        }
        return null;
    }

    public Date addCurrentDate(Date CurrentDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(CurrentDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> listRoomBusy = new ArrayList<>();
        if (checkInDate.after(checkOutDate)) {
            return null;
        }
        for (Reservation reserva : listReservation) {
            if (checkInDate.equals(reserva.getCheckInDate()) || checkOutDate.equals(reserva.getCheckOutDate())) {
                listRoomBusy.add(reserva.getRoom());
            } else if (checkOutDate.after(reserva.getCheckInDate()) && checkInDate.before(reserva.getCheckOutDate())) {
                listRoomBusy.add(reserva.getRoom());
            }
        }
        return roomDifference(listIRoom.values(), listRoomBusy);
    }

    private <T> Collection<T> roomDifference(Collection<T> firstListRoom, Collection<T> secondListRoom) {
        Collection<T> diff = new ArrayList<>(firstListRoom);
        diff.removeAll(secondListRoom);
        return diff;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> listReserva = new ArrayList<>();
        for (Reservation reserva : listReservation) {
            if (reserva.getCustomer().equals(customer)) {
                listReserva.add(reserva);
            }
        }
        return listReserva;
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getListReservation();
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("No reservations");
        }
    }

    Collection<Reservation> getListReservation() {
        return listReservation;
    }

    public Collection<IRoom> getListIRoom() {
        return listIRoom.values();
    }
}
