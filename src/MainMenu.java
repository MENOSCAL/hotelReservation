import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    public static int DayRecommended = 7;

    public static void mainMenu() {
        while (true) {
            playMainMenu();
        }
    }

    private static void playMainMenu() {
        String [] arrayOption = {"1", "2", "3", "4", "5"};
        while (true) {
            printMainMenu();
            String line = validInput("Please select a number for the menu option:", arrayOption);
            switch (line) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.adminMenu();
                case "5":
                    System.out.println("Exit");
                    System.exit(0);
                default:
                    System.out.println("Error");
            }
        }
    }

    private static void displayRooms(Collection<IRoom> rooms) {
        if (!rooms.isEmpty()) {
            rooms.forEach(System.out::println);
        } else {
            System.out.println("No rooms");
        }
    }

    private static void findAndReserveRoom() {
        String [] arrayOption = {"y", "n"};
        Scanner scanner = new Scanner(System.in);
        Date checkIn = validDate("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
        Date checkOut = validDate("Enter CheckOut Date month/day/year example 2/21/2020");

        /*List Room Available & Recommended*/
        Collection<IRoom> listRoom = hotelResource.findARoom(checkIn, checkOut);
        if (listRoom == null || listRoom.isEmpty()) {
            checkIn = hotelResource.addCurrentDate(checkIn, DayRecommended);
            checkOut = hotelResource.addCurrentDate(checkOut, DayRecommended);
            listRoom = hotelResource.findARoom(checkIn, checkOut);
            if (listRoom == null || listRoom.isEmpty()) {
                System.out.println("Rooms not found");
                return;
            }
            String pattern = "EEE MMM d yyyy";
            String checkInText = new SimpleDateFormat(pattern).format(checkIn);
            String checkOutText = new SimpleDateFormat(pattern).format(checkOut);
            System.out.println("Recommended rooms in this period: " +
                    "\n CheckIn Date: " + checkInText +
                    "\n CheckOut Date: " + checkOutText);
        }

        displayRooms(listRoom);
        String bookBool = validInput("Would you like to book a room? y/n", arrayOption);
        if (bookBool.equals("n")) {
            return;
        }
        String accountBool = validInput("Do you have an account with us? y/n", arrayOption);
        if (accountBool.equals("n")) {
            System.out.println("First create an account");
            return;
        }
        System.out.println("Enter Email format: name@domain.com");
        String email = scanner.nextLine().toLowerCase();
        if (hotelResource.getCustomer(email) == null) {
            System.out.println("Customer not found");
            return;
        }
        String roomNumber = validInputNumeric("What room number would like to reserve");

        /*List Reservation(1email -> many reservations)*/
        IRoom room = hotelResource.getRoom(roomNumber);
        if (listRoom.contains(room)) {
            Reservation reservation = hotelResource.bookARoom(email, room, checkIn, checkOut);
            if (reservation == null || listRoom.isEmpty()) {
                System.out.println("The reservation was not created");
            } else {
                System.out.println(reservation);
            }
        } else {
            System.out.println("The reservation was not created");
        }
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email format: name@domain.com");
        String email = scanner.nextLine().toLowerCase();
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
        if (reservations != null && !reservations.isEmpty()) {
            hotelResource.getCustomersReservations(email).forEach(System.out::println);
        } else {
            System.out.println("No reservations");
        }
    }

    private static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email format: name@domain.com");
        String email = scanner.nextLine().toLowerCase();
        System.out.println("First Name");
        String firstName = scanner.nextLine().toLowerCase();
        System.out.println("Last Name");
        String lastName = scanner.nextLine().toLowerCase();
        try {
            hotelResource.createACustomer(email, firstName, lastName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static Date validDate(String prompt) {
        String pattern = "M/d/yyyy";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(prompt);
            String option = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                LocalDate parsedDate = LocalDate.parse(option, formatter);
                return Date.from(parsedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                //return new SimpleDateFormat(pattern).parse(option);
            } catch (DateTimeParseException e) {
                System.out.println("Sorry, the option '"+ option + "' must be valid date. Try again!");
            }
        }
    }

    public static String validInput(String prompt, String [] options) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(prompt);
            String option = scanner.nextLine().toLowerCase();
            if (Arrays.asList(options).contains(option)){
                return option;
            }
            System.out.println("Sorry, the option '"+ option + "' is invalid. Try again!");
        }
    }

    public static String validInputNumeric(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(prompt);
            String option = scanner.nextLine();
            if (isInteger(option) || isDouble(option)){
                return option;
            }
            System.out.println("Sorry, the option '"+ option + "' must be numeric. Try again!");
        }
    }

    private static boolean isInteger(String stringValue) {
        try {
            Integer.parseInt(stringValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDouble(String stringValue) {
        try {
            Double.parseDouble(stringValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void printMainMenu() {
        String intro =  """
                  
                  Welcome to the Hotel Reservation Application
                  --------------------------------------------------
                  1. Find and reserve a room
                  2. See my reservations
                  3. Create an account
                  4. Admin
                  5. Exit
                  --------------------------------------------------""";
        System.out.println(intro);
    }
}
