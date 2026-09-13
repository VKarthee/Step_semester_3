
import java.util.Scanner;

class PatientVitals {

    private double[] readings;
    private int count;

    PatientVitals(double[] initialReadings) {
        readings = new double[500];
        count = 0;

        if (initialReadings != null) {
            for (int i = 0; i < initialReadings.length; i++) {
                recordReading(initialReadings[i]);
            }
        }
    }

    void recordReading(double reading) {
        if (reading <= 0 || reading > 45) {
            return;
        }

        if (count < 500) {
            readings[count] = reading;
            count++;
        }
    }

    double getAverage() {
        if (count == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < count; i++) {
            sum = sum + readings[i];
        }

        return sum / count;
    }

    double[] getAllReadings() {
        double[] result = new double[count];

        for (int i = 0; i < count; i++) {
            result[i] = readings[i];
        }

        return result;
    }
}

public class VitalsMonitoringGuard {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        double[] initialReadings = new double[n];

        for (int i = 0; i < n; i++) {
            initialReadings[i] = sc.nextDouble();
        }

        PatientVitals v = new PatientVitals(initialReadings);

        double[] result = v.getAllReadings();

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }

        System.out.println();

        System.out.println(v.getAverage());

        sc.close();
    }
}
