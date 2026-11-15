
import java.time.*;
import java.util.*;

abstract class Room {

    protected String roomName;
    protected double pricePerNight;

    public Room(String roomName, double price) {
        this.roomName = roomName;
        this.pricePerNight = price;
    }

    public abstract double calculatePrice(long days);

    public String getRoomName() {
        return roomName;
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(String name) {
        super(name, 200);
    }

    @Override
    public double calculatePrice(long days) {
        return pricePerNight * days;
    }
}

class StandardRoom extends Room {

    public StandardRoom(String name) {
        super(name, 150);
    }

    @Override
    public double calculatePrice(long days) {
        return pricePerNight * days;
    }
}

class Customer {

    String name;

    public Customer(String name) {
        this.name = name;
    }
}

class Reservation {

    Room room;
    Customer customer;
    LocalDate checkIn;
    LocalDate checkOut;
    LocalDateTime cancellationDeadline;
    boolean cancelled = false;

    public Reservation(Room room, Customer customer,
            LocalDate checkIn, LocalDate checkOut,
            LocalDateTime deadline) {
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cancellationDeadline = deadline;
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return !cancelled
                && start.isBefore(checkOut)
                && end.isAfter(checkIn);
    }
}

class BookingManager {

    private List<Reservation> reservations = new ArrayList<>();

    public void bookRoom(Room room, Customer customer,
            LocalDate start, LocalDate end,
            LocalDateTime deadline) {

        if (!end.isAfter(start)) {
            System.out.println("Invalid booking dates.");
            return;
        }

        for (Reservation r : reservations) {
            if (r.room == room && r.overlaps(start, end)) {
                System.out.println("Booking failed: "
                        + room.getRoomName()
                        + " is not available for "
                        + start + " to " + end + ".");
                return;
            }
        }

        Reservation reservation = new Reservation(
                room, customer, start, end, deadline);

        reservations.add(reservation);

        long days = java.time.temporal.ChronoUnit.DAYS
                .between(start, end);

        double price = room.calculatePrice(days);

        System.out.printf(
                "%s booked from %s to %s. Total price: $%.2f%n",
                room.getRoomName(), start, end, price);
    }

    public void cancelReservation(Room room, Customer customer) {
        for (Reservation r : reservations) {
            if (r.room == room
                    && r.customer == customer
                    && !r.cancelled) {

                if (LocalDateTime.now().isBefore(
                        r.cancellationDeadline)) {

                    r.cancelled = true;

                    System.out.println(
                            "Reservation for "
                            + room.getRoomName()
                            + " cancelled successfully.");
                } else {
                    System.out.println(
                            "Cancellation deadline has passed.");
                }

                return;
            }
        }

        System.out.println("Reservation not found.");
    }
}

public class HotelBooking {

    public static void main(String[] args) {

        Customer c1 = new Customer("John");

        Room deluxe = new DeluxeRoom("Deluxe Room 101");
        Room standard = new StandardRoom("Standard Room 205");

        BookingManager manager = new BookingManager();

        LocalDateTime deadline
                = LocalDateTime.of(2026, 11, 30, 23, 59);

        manager.bookRoom(
                deluxe, c1,
                LocalDate.of(2026, 12, 1),
                LocalDate.of(2026, 12, 5),
                deadline);

        manager.bookRoom(
                standard, c1,
                LocalDate.of(2026, 12, 3),
                LocalDate.of(2026, 12, 7),
                deadline);

        manager.bookRoom(
                deluxe, c1,
                LocalDate.of(2026, 12, 3),
                LocalDate.of(2026, 12, 7),
                deadline);

        manager.cancelReservation(deluxe, c1);
    }
}
