
class RaceEntry {

    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

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

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                RaceEntry entry = new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

class RunnerEntry extends RaceEntry {

    private String category;

    RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}

public class BibRegistration {

    public static void main(String[] args) {
        String[] bibNumbers = {"BIB1", "B1", "BIB2"};

        System.out.println(RaceEntry.registerBatch(bibNumbers, 80));
    }
}
