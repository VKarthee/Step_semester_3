
import java.util.*;

abstract class ShippingType {

    public abstract double calculateCharge(double weight);
}

class StandardShipping extends ShippingType {

    public double calculateCharge(double weight) {
        return 40 + 10 * weight;
    }
}

class ExpressShipping extends ShippingType {

    public double calculateCharge(double weight) {
        return 80 + 15 * weight;
    }
}

class FragileShipping extends ShippingType {

    private ShippingType standard = new StandardShipping();

    public double calculateCharge(double weight) {
        return standard.calculateCharge(weight) + 50;
    }
}

interface NotificationChannel {

    void notify(String parcelId, String status);
}

class SmsChannel implements NotificationChannel {

    public void notify(String id, String status) {
        System.out.println("[SMS] " + id + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {

    public void notify(String id, String status) {
        System.out.println("[Email] " + id + " is now " + status + ".");
    }
}

class Customer {

    String name;

    public Customer(String name) {
        this.name = name;
    }
}

class Parcel {

    private String parcelId;
    private double weight;
    private String status = "BOOKED";
    private ShippingType shipping;
    private List<NotificationChannel> channels = new ArrayList<>();

    private String[] stages = {
        "BOOKED",
        "PICKED_UP",
        "IN_TRANSIT",
        "OUT_FOR_DELIVERY",
        "DELIVERED"
    };

    public Parcel(String id, double weight, ShippingType shipping) {
        this.parcelId = id;
        this.weight = weight;
        this.shipping = shipping;
    }

    public void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    public void start() {
        System.out.println("Parcel " + parcelId
                + " booked (" + getShippingName()
                + ", " + weight + " kg).");

        System.out.printf("Charge: ₹%.2f%n",
                shipping.calculateCharge(weight));

        notifyChannels();
    }

    private String getShippingName() {
        if (shipping instanceof ExpressShipping) {
            return "Express";
        }
        if (shipping instanceof FragileShipping) {
            return "Fragile";
        }
        return "Standard";
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notify(parcelId, status);
        }
    }

    public void updateStatus(String newStatus) {
        int current = -1;
        int next = -1;

        for (int i = 0; i < stages.length; i++) {
            if (stages[i].equals(status)) {
                current = i;
            }
            if (stages[i].equals(newStatus)) {
                next = i;
            }
        }

        if (next != current + 1) {
            System.out.println("Invalid transition: "
                    + status + " → " + newStatus
                    + " is not allowed.");
            return;
        }

        status = newStatus;
        notifyChannels();
    }

    public void cancel() {
        if (!status.equals("BOOKED")) {
            System.out.println("Cancellation failed: "
                    + parcelId
                    + " can be cancelled only while BOOKED.");
            return;
        }

        status = "CANCELLED";
        System.out.println("Parcel " + parcelId + " cancelled.");
        notifyChannels();
    }
}

class ParcelService {

    public void bookParcel(Parcel parcel) {
        parcel.start();
    }
}

public class SwiftShip {

    public static void main(String[] args) {

        Customer customer = new Customer("John");

        Parcel parcel = new Parcel(
                "P101", 2, new ExpressShipping());

        parcel.subscribe(new SmsChannel());
        parcel.subscribe(new EmailChannel());

        ParcelService service = new ParcelService();

        service.bookParcel(parcel);

        parcel.updateStatus("PICKED_UP");

        parcel.cancel();

        parcel.updateStatus("IN_TRANSIT");

        parcel.updateStatus("DELIVERED");
    }
}
