
import java.util.*;

class Customer {

    private int id;
    private String name;

    Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer other = (Customer) obj;
        return id == other.id && name.equals(other.name);
    }

    public int hashCode() {
        return Objects.hash(id, name);
    }

    public String toString() {
        return "(" + id + ", " + name + ")";
    }
}

public class CustomerRegistry {

    public static void main(String[] args) {
        HashSet<Customer> customers = new HashSet<>();

        Customer c1 = new Customer(101, "Asha");
        Customer c2 = new Customer(101, "Asha");
        Customer c3 = new Customer(102, "Ravi");

        System.out.println(customers.add(c1));
        System.out.println(customers.add(c2) + " (duplicate rejected)");
        System.out.println(customers.add(c3));

        System.out.println("Unique count: " + customers.size());
        System.out.println("Contains: " + customers.contains(new Customer(101, "Asha")));
    }
}
