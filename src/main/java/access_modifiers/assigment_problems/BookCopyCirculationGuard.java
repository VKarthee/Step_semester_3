
import java.util.Scanner;

class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    BookInventory(int copiesTotal) {
        if (copiesTotal <= 0) {
            this.copiesTotal = 0;
            this.copiesAvailable = 0;
            return;
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    int getCopiesAvailable() {
        return copiesAvailable;
    }
}

public class q3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int totalCopies = sc.nextInt();

        int operations = sc.nextInt();

        BookInventory book = new BookInventory(totalCopies);

        for (int i = 0; i < operations; i++) {
            String operation = sc.next();

            if (operation.equals("OUT")) {
                book.checkOut();
            } else if (operation.equals("IN")) {
                book.checkIn();
            }
        }

        System.out.println(book.getCopiesAvailable());

        sc.close();
    }
}
