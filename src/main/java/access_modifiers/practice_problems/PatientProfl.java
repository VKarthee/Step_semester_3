
import java.util.Scanner;

class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPin;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
        this.lockerPin = null;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (patientId == null) {
            patientId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {
        lockerPin = pin;
    }
}

public class PatientProfile {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String patientId = sc.nextLine();

        String name = sc.nextLine();

        PatientProfile patient = new PatientProfile(patientId, name);

        System.out.println(patient.getPatientId());

        System.out.println(patient.getName());

        System.out.println(patient.isDischarged());

        patient.setDischarged(true);

        System.out.println(patient.isDischarged());

        patient.setLockerPin("1234");

        sc.close();
    }
}
