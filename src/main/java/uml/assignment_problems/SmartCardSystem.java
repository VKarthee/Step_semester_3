
import java.util.*;

interface PricingPlan {

    double calculatePrice(double originalPrice);
}

class DayScholarPlan implements PricingPlan {

    public double calculatePrice(double price) {
        return price;
    }
}

class HostellerPlan implements PricingPlan {

    public double calculatePrice(double price) {
        return price * 0.90;
    }
}

class StaffPlan implements PricingPlan {

    public double calculatePrice(double price) {
        return price * 0.80;
    }
}

class Transaction {

    String description;
    double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
}

class Purchase {

    String itemName;
    double chargedAmount;
    boolean refunded = false;

    public Purchase(String itemName, double chargedAmount) {
        this.itemName = itemName;
        this.chargedAmount = chargedAmount;
    }
}

class SmartCard {

    private String cardId;
    private PricingPlan plan;
    private List<Transaction> transactions = new ArrayList<>();
    private Map<String, Purchase> purchases = new LinkedHashMap<>();
    private boolean blocked = false;

    public SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
    }

    public double getBalance() {
        double balance = 0;

        for (Transaction t : transactions) {
            balance += t.amount;
        }

        return balance;
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up failed: Card is blocked.");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up failed: Minimum amount is ₹100.");
            return;
        }

        if (getBalance() + amount > 5000) {
            System.out.println("Top-up failed: Maximum balance is ₹5000.");
            return;
        }

        transactions.add(new Transaction("Top-up", amount));

        System.out.printf("%s topped up with ₹%.2f.%n",
                cardId, amount);

        System.out.printf("Balance: ₹%.2f%n", getBalance());
    }

    public void purchase(String itemName, double originalPrice) {
        if (blocked) {
            System.out.println("Purchase failed: Card is blocked.");
            return;
        }

        if (originalPrice <= 0) {
            System.out.println("Invalid item price.");
            return;
        }

        double chargedAmount = plan.calculatePrice(originalPrice);

        if (getBalance() < chargedAmount) {
            System.out.printf(
                    "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    chargedAmount, getBalance());
            return;
        }

        if (purchases.containsKey(itemName)) {
            System.out.println("Use a unique purchase item name.");
            return;
        }

        transactions.add(
                new Transaction(itemName, -chargedAmount));

        purchases.put(itemName,
                new Purchase(itemName, chargedAmount));

        System.out.printf("%s purchased for ₹%.2f.%n",
                itemName, chargedAmount);

        System.out.printf("Balance: ₹%.2f%n", getBalance());
    }

    public void refund(String itemName) {
        Purchase purchase = purchases.get(itemName);

        if (purchase == null) {
            System.out.println("Refund rejected: Purchase not found.");
            return;
        }

        if (purchase.refunded) {
            System.out.println("Refund rejected: "
                    + itemName + " has already been refunded.");
            return;
        }

        double amount = purchase.chargedAmount;

        if (getBalance() + amount > 5000) {
            System.out.println(
                    "Refund rejected: Maximum balance limit would be exceeded.");
            return;
        }

        transactions.add(
                new Transaction("Refund: " + itemName, amount));

        purchase.refunded = true;

        System.out.printf(
                "Refund of ₹%.2f for %s processed.%n",
                amount, itemName);

        System.out.printf("Balance: ₹%.2f%n", getBalance());
    }

    public void blockCard() {
        blocked = true;
        System.out.println(cardId + " is blocked.");
    }

    public void unblockCard() {
        blocked = false;
        System.out.println(cardId + " is unblocked.");
    }

    public void miniStatement() {
        System.out.print("Mini-statement for " + cardId + ": ");

        for (int i = 0; i < transactions.size(); i++) {
            double amount = transactions.get(i).amount;

            System.out.printf("%s%.2f",
                    amount >= 0 ? "+" : "", amount);

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(" = ₹%.2f.%n", getBalance());
    }
}

public class SmartCardSystem {

    public static void main(String[] args) {

        SmartCard card = new SmartCard(
                "C-2045", new HostellerPlan());

        card.topUp(500);

        card.purchase("Veg Thali", 120);

        card.purchase("Cold Coffee", 60);

        card.purchase("Extra Items", 400);

        card.refund("Veg Thali");

        card.refund("Veg Thali");

        card.miniStatement();
    }
}
