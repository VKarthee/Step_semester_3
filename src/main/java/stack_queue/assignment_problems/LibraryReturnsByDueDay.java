
import java.util.*;

class Node {

    String title;
    int day;
    Node next;

    Node(String title, int day) {
        this.title = title;
        this.day = day;
    }
}

public class LibraryReturnsByDueDay {

    static Node merge(Node a, Node b) {
        Node dummy = new Node("", 0);
        Node tail = dummy;
        while (a != null && b != null) {
            if (a.day <= b.day) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    static Node sortByDueDay(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node second = slow.next;
        slow.next = null;
        Node left = sortByDueDay(head);
        Node right = sortByDueDay(second);
        return merge(left, right);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Node head = null, tail = null;
        for (int i = 0; i < n; i++) {
            String[] input = sc.next().split(":");
            Node node = new Node(input[0], Integer.parseInt(input[1]));
            if (head == null) {
                head = node; 
            }else {
                tail.next = node;
            }
            tail = node;
        }
        head = sortByDueDay(head);
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.title + ":" + temp.day + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }
}
