
import java.util.*;

class Node {

    int data;
    Node next;

    Node(int data) {
        this.data = data;
    }
}

public class ExamScriptBatches {

    static Node reverseInGroups(Node head, int k) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node prevGroup = dummy;
        while (true) {
            Node kth = prevGroup;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) {
                break;
            }
            Node nextGroup = kth.next;
            Node prev = nextGroup;
            Node curr = prevGroup.next;
            while (curr != nextGroup) {
                Node next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            Node oldHead = prevGroup.next;
            prevGroup.next = kth;
            prevGroup = oldHead;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Node head = null, tail = null;
        for (int i = 0; i < n; i++) {
            Node node = new Node(sc.nextInt());
            if (head == null) {
                head = node; 
            }else {
                tail.next = node;
            }
            tail = node;
        }
        int k = sc.nextInt();
        head = reverseInGroups(head, k);
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }
}
