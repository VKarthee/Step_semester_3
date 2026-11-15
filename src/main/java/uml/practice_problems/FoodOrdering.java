
import java.util.*;

interface PaymentMethod {

    boolean pay(double amount);

    String getName();
}

class CreditCardPayment implements PaymentMethod {

    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements PaymentMethod {

    @Override
    public boolean pay(double amount) {
        return false;
    }

    @Override
    public String getName() {
        return "Digital Wallet";
    }
}

class FoodItem {

    String name;
    double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class LineItem {

    FoodItem item;
    int quantity;

    public LineItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getTotal() {
        return item.price * quantity;
    }
}

class Customer {

    String name;

    public Customer(String name) {
        this.name = name;
    }
}

class Order {

    private static int counter = 122;
    private int orderId;
    private Customer customer;
    private List<LineItem> items = new ArrayList<>();
    private String status = "Created";

    public Order(Customer customer) {
        this.customer = customer;
        this.orderId = ++counter;
        System.out.println("Order created.");
    }

    public void addItem(FoodItem item, int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        items.add(new LineItem(item, quantity));

        System.out.println("Added " + item.name
                + " (Qty " + quantity + ")");
    }

    public double calculateTotal() {
        double total = 0;

        for (LineItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public void placeOrder(PaymentMethod payment) {
        if (items.isEmpty()) {
            System.out.println(
                    "Cannot place order: Order must contain at least one item.");
            return;
        }

        status = "Pending Payment";
        System.out.println("Order placed successfully.");

        boolean success = payment.pay(calculateTotal());

        if (success) {
            status = "Paid";

            System.out.println("Payment via "
                    + payment.getName() + " successful.");

            System.out.println("Order status: " + status);

            System.out.println("Notification: Order #"
                    + orderId + " placed and paid.");
        } else {
            System.out.println("Payment via "
                    + payment.getName() + " failed.");

            System.out.println("Order status: " + status);

            System.out.println("Notification: Order #"
                    + orderId + " placed, awaiting payment.");
        }
    }
}

public class FoodOrdering {

    public static void main(String[] args) {

        Customer customer = new Customer("John");

        FoodItem pizza = new FoodItem("Pizza", 200);
        FoodItem soda = new FoodItem("Soda", 50);
        FoodItem burger = new FoodItem("Burger", 150);

        Order order1 = new Order(customer);

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        Order emptyOrder = new Order(customer);

        emptyOrder.placeOrder(new CreditCardPayment());

        order1.placeOrder(new CreditCardPayment());

        Order order2 = new Order(customer);

        order2.addItem(burger, 1);

        order2.placeOrder(new DigitalWalletPayment());
    }
}
