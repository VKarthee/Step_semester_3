
class DischargeSummary {

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        this.patientId = patientId;

        if (medicationCodes == null) {
            this.medicationCodes = null;
            return;
        }

        for (int i = 0; i < medicationCodes.length; i++) {
            if (medicationCodes[i] == null || !medicationCodes[i].matches("MED-[A-Z]")) {
                this.medicationCodes = null;
                return;
            }
        }

        this.medicationCodes = medicationCodes.clone();
    }

    public String[] getMedicationCodes() {
        if (medicationCodes == null) {
            return null;
        }

        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (medicationCodes == null) {
            return null;
        }

        if (index < 0 || index >= medicationCodes.length) {
            return null;
        }

        if (newCode == null || !newCode.matches("MED-[A-Z]")) {
            return null;
        }

        String[] correctedCodes = medicationCodes.clone();

        correctedCodes[index] = newCode;

        return new DischargeSummary(patientId, correctedCodes);
    }

    public String getPatientId() {
        return patientId;
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {

    private int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}

class NightlyProcessor {

    static int totalProcessed;
    static int criticalCareCount;
    static int routineCount;

    static {
        totalProcessed = 0;
        criticalCareCount = 0;
        routineCount = 0;
    }

    static String processNightlyBatch(DischargeSummary[] summaries) {
        int nullSkipped = 0;

        for (int i = 0; i < summaries.length; i++) {
            if (summaries[i] == null) {
                nullSkipped++;
                continue;
            }

            totalProcessed++;

            if (summaries[i] instanceof CriticalCareDischargeSummary) {
                criticalCareCount++;
            } else {
                routineCount++;
            }
        }

        return totalProcessed + " processed | " + nullSkipped + " null skipped | " + criticalCareCount + " critical-care | " + routineCount + " routine";
    }
}

public class q5 {

    public static void main(String[] args) {
        String[] medicines1 = {"MED-X"};

        String[] medicines2 = {"MED-Y"};

        DischargeSummary[] summaries = new DischargeSummary[3];

        summaries[0] = new CriticalCareDischargeSummary("MT001", medicines1, 4);

        summaries[1] = null;

        summaries[2] = new DischargeSummary("MT002", medicines2);

        System.out.println(NightlyProcessor.processNightlyBatch(summaries));
    }
}
