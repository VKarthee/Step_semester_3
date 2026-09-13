
class EventTicket {

    protected double balanceDue;

    public EventTicket(double basePrice) {
        balanceDue = basePrice;
    }

    public String printTicket() {
        return "Standard | Balance: " + balanceDue;
    }

    public double getBalanceDue() {
        return balanceDue;
    }
}

class WorkshopTicket extends EventTicket {

    private String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + track + " | Balance: " + balanceDue;
    }
}

public class NightlyTicketAnnouncer {

    static String batchPrint(EventTicket[] tickets) {

        StringBuilder report = new StringBuilder();

        for (EventTicket ticket : tickets) {

            report.append(ticket.printTicket());
            report.append(" | ");

            if (ticket instanceof WorkshopTicket) {

                WorkshopTicket workshop = (WorkshopTicket) ticket;

                report.append("[Track via downcast: ");
                report.append(workshop.getTrack());
                report.append("] | ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {

        EventTicket ticket1 = new EventTicket(500);

        WorkshopTicket ticket2 = new WorkshopTicket(1200, "AI/ML");

        EventTicket[] tickets = {
            ticket1,
            ticket2
        };

        System.out.println(batchPrint(tickets));
    }
}
