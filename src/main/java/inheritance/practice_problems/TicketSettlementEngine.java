
class EventTicket {

    private static int ticketsIssued = 1000;

    private final String ticketId;

    protected double balanceDue;

    public EventTicket(double basePrice) {

        ticketsIssued++;

        ticketId = "TCK-" + ticketsIssued;

        balanceDue = basePrice;
    }

    public void pay(double amount) {

        balanceDue = balanceDue - amount;

        if (balanceDue < 0) {
            balanceDue = 0;
        }
    }

    public void pay(double amount, String mode) {

        System.out.println("Payment Mode: " + mode);

        pay(amount);
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public static boolean isValidPromoCode(String code) {

        if (code == null) {
            return false;
        }

        if (code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'F') {
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

    public static int getTicketsIssued() {
        return ticketsIssued - 1000;
    }
}

class GroupTicket extends EventTicket {

    private int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class TicketSettlementEngine {

    static String processNightlySettlement(EventTicket[] tickets) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {

            if (ticket == null) {
                nullSkipped++;
            } else {

                processed++;

                if (ticket instanceof GroupTicket) {
                    group++;
                } else {
                    individual++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {

        EventTicket ticket1 = new EventTicket(500);

        System.out.println(ticket1.getTicketId());

        System.out.println(EventTicket.getTicketsIssued());

        System.out.println(EventTicket.isValidPromoCode("F123A"));

        System.out.println(EventTicket.isValidPromoCode("F12A"));

        System.out.println(EventTicket.isValidPromoCode("X123A"));

        ticket1.pay(200);

        ticket1.pay(200, "UPI");

        System.out.println(ticket1.getBalanceDue());

        GroupTicket groupTicket = new GroupTicket(2000, 5);

        EventTicket ticket2 = new EventTicket(500);

        EventTicket[] tickets = {groupTicket, null, ticket2};

        System.out.println(processNightlySettlement(tickets));
    }
}
