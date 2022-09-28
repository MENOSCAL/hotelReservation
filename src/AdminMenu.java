import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public static void adminMenu() {
        while (true) {
            playAdminMenu();
        }
    }

    private static void playAdminMenu() {
        String [] arrayOption = {"1", "2", "3", "4", "5", "6"};
        while (true) {
            printAdminMenu();
            String line = MainMenu.validInput("Please select a number for the menu option:", arrayOption);
            switch (line) {
                case "1":
                    displayAllCustomers();
                    break;
                case "2":
                    displayAllRooms();
                    break;
                case "3":
                    adminResource.displayAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    addTestData();
                    break;
                case "6":
                    MainMenu.mainMenu();
                default:
                    System.out.println("Error");
            }
        }
    }

    private static void displayAllCustomers() {
        if (!adminResource.getAllCustomers().isEmpty()) {
            adminResource.getAllCustomers().forEach(System.out::println);
        } else {
            System.out.println("No customers");
        }
    }

    private static void displayAllRooms() {
        if (!adminResource.getAllRooms().isEmpty()) {
            adminResource.getAllRooms().forEach(System.out::println);
        } else {
            System.out.println("No rooms");
        }
    }

    private static void addRoom() {
        String [] arrayOption = {"y", "n"};
        String roomNumber = MainMenu.validInputNumeric("Enter room number");
        Double roomPrice = Double.parseDouble(MainMenu.validInputNumeric("Enter price per night"));
        RoomType roomType = enterRoomType();

        IRoom room = new Room(roomNumber, roomPrice, roomType);
        adminResource.addRoom(Collections.singletonList(room));
        String line = MainMenu.validInput("Would you like to add another room y/n", arrayOption);
        if (line.equals("y")) {
            addRoom();
        }
    }

    private static RoomType enterRoomType() {
        String [] arrayOption = {"1", "2"};
        String line = MainMenu.validInput("Enter room type: 1 for single bed, 2 for double bed", arrayOption);
        if (line.equals("1")) {
            return RoomType.SINGLE;
        } else {
            return RoomType.DOUBLE;
        }
    }

    private static void addTestData() {
        Date checkIn;
        Date checkOut;
        LocalDate parsedDate;
        IRoom room;
        String pattern = "M/d/yyyy";
        List<IRoom> rooms = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            if (i % 2 == 0) {
                room = new Room(Integer.toString(100 *i), 135.0 * i, RoomType.DOUBLE);
            } else {
                room = new Room(Integer.toString(100 *i), 135.0 * i, RoomType.SINGLE);
            }
            rooms.add(room);
        }

        adminResource.addRoom(rooms);

        hotelResource.createACustomer("jef@gmail.com", "Jeff", "Phillips");
        hotelResource.createACustomer("vic@gmail.com", "Victor", "Menoscal");

        parsedDate = LocalDate.parse("01/01/2022", DateTimeFormatter.ofPattern(pattern));
        checkIn = Date.from(parsedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        parsedDate = LocalDate.parse("01/07/2022", DateTimeFormatter.ofPattern(pattern));
        checkOut = Date.from(parsedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        hotelResource.bookARoom("vic@gmail.com", hotelResource.getRoom("100"), checkIn, checkOut);
        hotelResource.bookARoom("jef@gmail.com", hotelResource.getRoom("200"), checkIn, checkOut);

        System.out.println("Data entered correctly");
    }

    private static void printAdminMenu() {
        String intro =  """
                  
                  Admin Menu
                  --------------------------------------------------
                  1. See all Customers
                  2. See all Rooms
                  3. See all Reservations
                  4. Add a Room
                  5. Add Test Data
                  6. Back to Main Menu
                  --------------------------------------------------""";
        System.out.println(intro);
    }
}
