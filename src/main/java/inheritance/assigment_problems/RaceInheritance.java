
class RaceEntry {

    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        amountPaid = amountPaid + amount;
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public void announce() {
        System.out.println("Race Entry: " + bibNumber);
    }
}

class RunnerEntry extends RaceEntry {

    private String category;

    RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public void announce() {
        System.out.println("Runner Entry");
    }
}

class EliteRunnerEntry extends RunnerEntry {

    EliteRunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee, category);
    }

    @Override
    public void announce() {
        System.out.println("Elite Runner Entry");
    }
}

class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    @Override
    public void announce() {
        System.out.println("Relay Team Entry");
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class RaceInheritance {

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "EliteRunnerEntry";
        }

        if (entry instanceof RunnerEntry) {
            return "RunnerEntry";
        }

        if (entry instanceof RelayTeamEntry) {
            return "RelayTeamEntry";
        }

        return "RaceEntry";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;

        for (RaceEntry entry : entries) {
            total = total + entry.getBalanceDue();
        }

        return total;
    }

    public static void main(String[] args) {
        RaceEntry r1 = new RaceEntry("R001", 100);
        RunnerEntry r2 = new RunnerEntry("R002", 150, "General");
        EliteRunnerEntry r3 = new EliteRunnerEntry("R003", 200, "Elite");
        RelayTeamEntry r4 = new RelayTeamEntry("R004", 300, 4);

        System.out.println(classifyGeneration(r1));
        System.out.println(classifyGeneration(r2));
        System.out.println(classifyGeneration(r3));
        System.out.println(classifyGeneration(r4));

        RaceEntry[] entries = {r1, r2, r3, r4};

        System.out.println("Total Balance Due: " + getTotalBalanceDue(entries));

        r1.announce();
        r2.announce();
        r3.announce();
        r4.announce();
    }
}
