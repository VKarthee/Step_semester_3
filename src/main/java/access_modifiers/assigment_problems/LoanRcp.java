
import java.util.Scanner;

final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;

        if (bookIds == null) {
            this.bookIds = null;
            return;
        }

        for (int i = 0; i < bookIds.length; i++) {
            if (bookIds[i] == null || !bookIds[i].matches("BK-\\d{3}")) {
                this.bookIds = null;
                return;
            }
        }

        this.bookIds = bookIds.clone();
    }

    public String[] getBookIds() {
        if (bookIds == null) {
            return null;
        }

        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (bookIds == null) {
            return null;
        }

        if (index < 0 || index >= bookIds.length) {
            return null;
        }

        if (newId == null || !newId.matches("BK-\\d{3}")) {
            return null;
        }

        String[] correctedIds = bookIds.clone();

        correctedIds[index] = newId;

        return new LoanReceipt(memberId, correctedIds);
    }
}

public class LoanRcp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String memberId = sc.nextLine();

        int n = sc.nextInt();

        String[] bookIds = new String[n];

        for (int i = 0; i < n; i++) {
            bookIds[i] = sc.next();
        }

        LoanReceipt receipt = new LoanReceipt(memberId, bookIds);

        String[] result = receipt.getBookIds();

        if (result == null) {
            System.out.println("Invalid");
        } else {
            System.out.println(memberId);

            for (int i = 0; i < result.length; i++) {
                System.out.println(result[i]);
            }
        }

        sc.close();
    }
}
