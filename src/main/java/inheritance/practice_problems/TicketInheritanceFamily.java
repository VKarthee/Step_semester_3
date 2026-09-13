
class EventTicket {

    protected double basePrice;
    protected double balanceDue;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + balanceDue;
    }
}

class WorkshopTicket extends EventTicket {

    protected String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String printTicket() {
        return "Workshop Ticket | Track: " + track + " | Balance Due: " + balanceDue;
    }
}

class PremiumWorkshopTicket extends WorkshopTicket {

    private double kitFee;

    public PremiumWorkshopTicket(double basePrice, String track, double kitFee) {
        super(basePrice, track);
        this.kitFee = kitFee;
    }

    public String printTicket() {
        return "Premium Workshop Ticket | Track: " + track + " | Kit Fee: " + kitFee + " | Balance Due: " + balanceDue;
    }
}

class HackathonTicket extends EventTicket {

    private String teamName;

    public HackathonTicket(double basePrice, String teamName) {
        super(basePrice);
        this.teamName = teamName;
    }

    public String printTicket() {
        return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + balanceDue;
    }
}

public class TicketInheritanceFamily {

    static String classifyGeneration(EventTicket ticket) {

        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }

        if (ticket instanceof WorkshopTicket) {
            return "Direct Workshop descendant";
        }

        return "Standard Event Ticket";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {

        double total = 0;

        for (EventTicket ticket : tickets) {
            total = total + ticket.getBalanceDue();
        }

        return total;
    }

    public static void main(String[] args) {

        EventTicket standard = new EventTicket(500);

        WorkshopTicket workshop = new WorkshopTicket(1200, "AI/ML");

        PremiumWorkshopTicket premium = new PremiumWorkshopTicket(2000, "Cloud Native", 300);

        HackathonTicket hackathon = new HackathonTicket(800, "Byte Force");

        System.out.println(standard.printTicket());

        System.out.println(workshop.printTicket());

        System.out.println(premium.printTicket());

        System.out.println(hackathon.printTicket());

        System.out.println(classifyGeneration(premium));

        System.out.println(classifyGeneration(hackathon));

        EventTicket[] tickets = {
            standard,
            workshop,
            premium,
            hackathon
        };

        System.out.println(getTotalBalanceDue(tickets));
    }
}
