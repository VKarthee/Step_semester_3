
import java.util.Arrays;

class EventTicket {

    protected double balanceDue;

    private double[] lateFeeHistory;
    private int lateFeeCount;

    public EventTicket(double basePrice) {
        balanceDue = basePrice;
        lateFeeHistory = new double[10];
        lateFeeCount = 0;
    }

    protected void applyLateFee(double amount) {
        balanceDue = balanceDue + amount;

        if (lateFeeCount < 10) {
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
        }
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, lateFeeCount);
    }
}

class WorkshopTicket extends EventTicket {

    public WorkshopTicket(double basePrice) {
        super(basePrice);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class LateFeeAuditTrail {

    public static void main(String[] args) {

        WorkshopTicket ticket = new WorkshopTicket(1200);

        ticket.applyLateFee(100);

        System.out.println(ticket.getBalanceDue());

        double[] history = ticket.getLateFeeHistory();

        System.out.println(history[0]);

        history[0] = 9999;

        double[] newHistory = ticket.getLateFeeHistory();

        System.out.println(newHistory[0]);
    }
}
