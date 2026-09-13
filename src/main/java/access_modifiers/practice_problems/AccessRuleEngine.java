
class PatientRecord {

    String patientId;
    String wardCode;
    double vitalsScore;
    String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        this.patientId = patientId.trim();
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;

        if (patientId.length() <= 3) {
            System.out.println("construction rejected");
            System.exit(1);
        }

    }

    static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "private":
                if (accessorContext.equals("SAME_CLASS")) {
                    return "ALLOWED";
                } else {
                    return "DENIED";
                }
            case "default":
                if (accessorContext.equals("DIFFERENT_PACKAGE")) {
                    return "DENIED";
                } else {
                    return "ALLOWED";
                }
            case "protected":
                if (accessorContext.equals("UNRELATED_CLASS") || accessorContext.equals("DIFFERENT_PACKAGE")) {
                    return "DENIED";
                } else {
                    return "ALLOWED";
                }
            case "public":
                return "ALLOWED";
            default:
                return "Invalid Access Modifier";
        }
    }

    static String summarizeBatch(String[][] attempts) {
        int allowed = 0, denied = 0;

        for (int i = 0; i < 3; i++) {
            switch (attempts[i][0]) {
                case "private":
                    if (attempts[i][1].equals("SAME_CLASS")) {
                        allowed++;
                    } else {
                        denied++;
                    }
                    break;
                case "default":
                    if (attempts[i][1].equals("DIFFERENT_PACKAGE")) {
                        denied++;
                    } else {
                        allowed++;
                    }
                    break;
                case "protected":
                    if (attempts[i][1].equals("UNRELATED_CLASS") || attempts[i][1].equals("DIFFERENT_PACKAGE")) {
                        denied++;
                    } else {
                        allowed++;
                    }
                    break;
                case "public":
                    allowed++;
                    break;
                default:
                    denied++;
                    break;

            }
        }
        return "ALLOWED: " + allowed + " | DENIED: " + denied;
    }
}

public class AccessRuleEngine {

    public static void main(String[] args) {
        PatientRecord p1 = new PatientRecord("MT999", "W3", 98.2, "MediTrack Central");
        System.out.println(p1.classifyAccess("default", "DIFFERENT_PACKAGE"));
        System.out.println(p1.classifyAccess("private", "SAME_CLASS"));
        String[][] attempts = {{"protected", "SAME_PACKAGE"}, {"protected", "DIFFERENT_PACKAGE"}, {"public", "DIFFERENT_PACKAGE"}};
        System.out.println(p1.summarizeBatch(attempts));

        PatientRecord p2 = new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
    }
}
