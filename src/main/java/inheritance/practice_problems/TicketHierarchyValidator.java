
class EventTicket {

    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Invalid base price");
        }

        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public void pay(double amount) {
        balanceDue = balanceDue - amount;

        if (balanceDue < 0) {
            balanceDue = 0;
        }
    }

    public double getBalanceDue() {
        return balanceDue;
    }
}

class WorkshopTicket extends EventTicket {

    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }
}

public class TicketEvent {

    static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        for (String attendeeId : attendeeIds) {
            try {
                EventTicket ticket = new EventTicket(attendeeId, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {

        EventTicket ticket1 = new EventTicket("STU2", 1200);

        ticket1.pay(500);

        System.out.println(ticket1.getBalanceDue());

        WorkshopTicket ticket2 = new WorkshopTicket("STU2", 1200, "AI/ML");

        ticket2.pay(500);

        System.out.println(ticket2.getBalanceDue());

        String[] attendees = {"STU1", "ST1", "STU2", "  ", "STU3"};

        System.out.println(registerBatch(attendees, 500));
    }
}
