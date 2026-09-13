
import java.util.Scanner;

class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    public LibraryMember() {
        this(null, null);
    }

    public LibraryMember(String name) {
        this(null, name);
    }

    public LibraryMember(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
        this.securityAnswer = null;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (membershipId == null) {
            membershipId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {
        securityAnswer = answer.toUpperCase();
    }
}

public class q4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String membershipId = sc.nextLine();

        String name = sc.nextLine();

        LibraryMember member = new LibraryMember(membershipId, name);

        System.out.println(member.getMembershipId());

        System.out.println(member.getName());

        System.out.println(member.isPremiumMember());

        member.setPremiumMember(true);

        System.out.println(member.isPremiumMember());

        member.setSecurityAnswer("blue");

        sc.close();
    }
}
