
abstract class Vehicle {

    protected String name;
    protected double dailyRate;
    private boolean available = true;

    public Vehicle(String name, double dailyRate) {
        this.name = name;
        this.dailyRate = dailyRate;
    }

    public abstract double calculateCharge(int days);

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }
}

class LuxuryCar extends Vehicle {

    public LuxuryCar(String name) {
        super(name, 100);
    }

    @Override
    public double calculateCharge(int days) {
        return dailyRate * days;
    }
}

class StandardCar extends Vehicle {

    public StandardCar(String name) {
        super(name, 50);
    }

    @Override
    public double calculateCharge(int days) {
        return dailyRate * days;
    }
}

class Rental {

    Vehicle vehicle;
    int days;

    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }
}

class RentalService {

    public void rentVehicle(Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getName()
                    + " is already rented.");
            return;
        }

        if (days <= 0) {
            System.out.println("Invalid rental duration.");
            return;
        }

        vehicle.setAvailable(false);

        double charge = vehicle.calculateCharge(days);

        System.out.printf(
                "%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getName(), days, charge);
    }

    public void returnVehicle(Vehicle vehicle) {
        if (vehicle.isAvailable()) {
            System.out.println(vehicle.getName()
                    + " is not currently rented.");
            return;
        }

        vehicle.setAvailable(true);

        System.out.println(vehicle.getName()
                + " returned. Now available.");
    }
}

public class VehicleRental {

    public static void main(String[] args) {

        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");

        RentalService service = new RentalService();

        service.rentVehicle(luxury, 3);
        service.rentVehicle(standard, 5);

        service.returnVehicle(luxury);

        service.rentVehicle(luxury, 2);
    }
}
