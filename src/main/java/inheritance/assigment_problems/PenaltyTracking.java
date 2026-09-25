
class RaceEntry {

    private double lateFee;

    public void applyLateFee(double amount) {
        lateFee = lateFee + amount;
    }

    public double getLateFee() {
        return lateFee;
    }
}

class RunnerEntry extends RaceEntry {

    private double[] lateFeeHistory = new double[10];
    private int historyCount = 0;

    @Override
    public void applyLateFee(double amount) {
        double actualFee = amount * 2;

        super.applyLateFee(actualFee);

        lateFeeHistory[historyCount] = actualFee;
        historyCount++;
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[historyCount];

        for (int i = 0; i < historyCount; i++) {
            copy[i] = lateFeeHistory[i];
        }

        return copy;
    }
}

public class PenaltyTracking {

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry();

        runner.applyLateFee(50);
        runner.applyLateFee(30);

        System.out.println("Total Late Fee: " + runner.getLateFee());

        double[] history = runner.getLateFeeHistory();

        for (int i = 0; i < history.length; i++) {
            System.out.println("Penalty: " + history[i]);
        }
    }
}
