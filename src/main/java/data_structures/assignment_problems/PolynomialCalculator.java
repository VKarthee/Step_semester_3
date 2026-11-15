
class Node {

    int coefficient;
    int exponent;
    Node next;

    Node(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.next = null;
    }
}

public class PolynomialCalculator {

    public static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;

        while (p != null && q != null) {
            if (p.exponent > q.exponent) {
                tail.next = new Node(p.coefficient, p.exponent);
                p = p.next;
            } else if (p.exponent < q.exponent) {
                tail.next = new Node(q.coefficient, q.exponent);
                q = q.next;
            } else {
                int sum = p.coefficient + q.coefficient;

                if (sum != 0) {
                    tail.next = new Node(sum, p.exponent);
                }

                p = p.next;
                q = q.next;

                if (sum == 0) {
                    continue;
                }
            }

            tail = tail.next;
        }

        while (p != null) {
            tail.next = new Node(p.coefficient, p.exponent);
            tail = tail.next;
            p = p.next;
        }

        while (q != null) {
            tail.next = new Node(q.coefficient, q.exponent);
            tail = tail.next;
            q = q.next;
        }

        return dummy.next;
    }

    public static void printPolynomial(Node head) {
        if (head == null) {
            System.out.println("0");
            return;
        }

        Node current = head;
        boolean first = true;

        while (current != null) {
            int coefficient = current.coefficient;
            int exponent = current.exponent;

            if (!first) {
                System.out.print(coefficient >= 0 ? " + " : " - ");
            } else if (coefficient < 0) {
                System.out.print("-");
            }

            int absoluteCoefficient = Math.abs(coefficient);

            if (exponent == 0 || absoluteCoefficient != 1) {
                System.out.print(absoluteCoefficient);
            }

            if (exponent > 0) {
                System.out.print("x");
                if (exponent > 1) {
                    System.out.print("^" + exponent);
                }
            }

            first = false;
            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        Node p = new Node(5, 3);
        p.next = new Node(4, 2);
        p.next.next = new Node(2, 0);

        Node q = new Node(5, 2);
        q.next = new Node(-3, 1);
        q.next.next = new Node(1, 0);

        Node result = addPolynomials(p, q);

        System.out.print("Sample 1: ");
        printPolynomial(result);

        Node p2 = new Node(3, 2);
        p2.next = new Node(1, 0);

        Node q2 = new Node(-3, 2);
        q2.next = new Node(4, 1);

        Node result2 = addPolynomials(p2, q2);

        System.out.print("Sample 2: ");
        printPolynomial(result2);
    }
}
