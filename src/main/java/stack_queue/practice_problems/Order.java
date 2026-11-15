
import java.util.*;

class Order {

    static class LineItem {

        String productName;
        int quantity;
        double unitPrice;

        LineItem(String p, int q, double u) {
            productName = p;
            quantity = q;
            unitPrice = u;
        }

        double total() {
            return quantity * unitPrice;
        }
    }
    LineItem[] items;
    int count = 0;

    Order(int n) {
        items = new LineItem[n];
    }

    void addItem(String p, int q, double u) {
        items[count++] = new LineItem(p, q, u);
    }

    class Cursor {

        int index = 0;

        boolean hasNext() {
            return index < count;
        }

        LineItem next() {
            return items[index++];
        }

        void reset() {
            index = 0;
        }
    }

    void display() {
        Cursor c = new Cursor();
        double sum = 0;
        while (c.hasNext()) {
            LineItem item = c.next();
            System.out.println(item.productName + " " + item.total());
            sum += item.total();
        }
        System.out.println("Total " + sum);
        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Order o = new Order(n);
        for (int i = 0; i < n; i++) {
            o.addItem(sc.next(), sc.nextInt(), sc.nextDouble());
        }
        o.display();
    }
}
