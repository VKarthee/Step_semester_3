
import java.util.*;

abstract class Ride {

    double distance;

    Ride(double d) {
        distance = d;
    }

    abstract double calculateFare();
}

class Auto extends Ride {

    Auto(double d) {
        super(d);
    }

    double calculateFare() {
        return 30 + 15 * distance;
    }
}

class Cab extends Ride {

    double surge;

    Cab(double d, double s) {
        super(d);
        surge = s;
    }

    double calculateFare() {
        return (50 + 20 * distance) * surge;
    }
}

class Bike extends Ride {

    int waiting;

    Bike(double d, int w) {
        super(d);
        waiting = w;
    }

    double calculateFare() {
        return 20 + 10 * distance + 2 * waiting;
    }
}

class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Ride[] rides = new Ride[n];
        for (int i = 0; i < n; i++) {
            String type = sc.next();
            double d = sc.nextDouble();
            if (type.equalsIgnoreCase("Auto")) {
                rides[i] = new Auto(d); 
            }else if (type.equalsIgnoreCase("Cab")) {
                sc.next();
                double s = sc.nextDouble();
                rides[i] = new Cab(d, s);
            } else {
                sc.next();
                int w = sc.nextInt();
                rides[i] = new Bike(d, w);
            }
        }
        double total = 0;
        for (Ride r : rides) {
            double fare = r.calculateFare();
            System.out.println(r.getClass().getSimpleName() + ": " + fare);
            total += fare;
        }
        System.out.println("Total: " + total);
    }
}
