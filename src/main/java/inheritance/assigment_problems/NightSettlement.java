
class RaceEntry {

    private static int bibCounter = 0;

    private final int entryCode;
    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;

        bibCounter++;
        entryCode = bibCounter;
    }

    public void pay(double amount) {
        amountPaid = amountPaid + amount;
    }

    public void pay(double amount, String mode) {
        pay(amount);
        System.out.println("Payment Mode: " + mode);
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public int getEntryCode() {
        return entryCode;
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getBibCounter() {
        return bibCounter;
    }
}

class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class NightSettlement {

    public static String settleNight(RaceEntry[] entries) {
        StringBuilder result = new StringBuilder();

        for (RaceEntry entry : entries) {
            if (entry == null) {
                continue;
            }

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                result.append("Relay Team - Size: " + relay.getTeamSize() + ", Balance: " + relay.getBalanceDue() + "\n");
            } else {
                result.append("Race Entry - Balance: " + entry.getBalanceDue() + "\n");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        RaceEntry r1 = new RaceEntry("R001", 100);
        RaceEntry r2 = new RaceEntry("R002", 150);
        RelayTeamEntry r3 = new RelayTeamEntry("R003", 300, 4);
        RaceEntry r4 = new RaceEntry("R004", 200);

        r1.pay(50);
        r2.pay(100, "UPI");
        r3.pay(300);
        r4.pay(50);

        System.out.println("Valid M123A: " + RaceEntry.isValidDiscountCode("M123A"));
        System.out.println("Valid M12aA: " + RaceEntry.isValidDiscountCode("M12aA"));

        System.out.println("Bib Counter: " + RaceEntry.getBibCounter());

        System.out.println(NightSettlement.settleNight(new RaceEntry[]{r1, r2, r3, null, r4}));
    }
}
