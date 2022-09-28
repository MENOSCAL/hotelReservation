package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource single_instance;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static HotelResource getInstance() {
        if (single_instance == null) {
            single_instance = new HotelResource();
        }
        return single_instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, CheckOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Date addCurrentDate(Date CurrentDate, int days) {
        return reservationService.addCurrentDate(CurrentDate, days);
    }
}
