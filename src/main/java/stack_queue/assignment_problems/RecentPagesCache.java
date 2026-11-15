
import java.util.*;

public class RecentPagesCache {

    static class Node {

        int key;
        String page;
        Node prev, next;

        Node(int k, String p) {
            key = k;
            page = p;
        }
    }
    int capacity;
    HashMap<Integer, Node> map = new HashMap<>();
    Node head = new Node(0, ""), tail = new Node(0, "");

    RecentPagesCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    void insert(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    String get(int key) {
        if (!map.containsKey(key)) {
            return "-1";
        }
        Node node = map.get(key);
        remove(node);
        insert(node);
        return node.page;
    }

    String put(int key, String page) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.page = page;
            remove(node);
            insert(node);
            return "updated";
        }
        String result = "added";
        if (map.size() == capacity) {
            Node lru = tail.prev;
            result = "evicted " + lru.key;
            map.remove(lru.key);
            remove(lru);
        }
        Node node = new Node(key, page);
        map.put(key, node);
        insert(node);
        return result;
    }

    void display() {
        System.out.print("Cache (most recent first): ");
        Node temp = head.next;
        while (temp != tail) {
            System.out.print(temp.key + "=" + temp.page);
            temp = temp.next;
            if (temp != tail) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int capacity = sc.nextInt(), n = sc.nextInt();
        RecentPagesCache cache = new RecentPagesCache(capacity);
        for (int i = 0; i < n; i++) {
            String operation = sc.next();
            int key = sc.nextInt();
            if (operation.equalsIgnoreCase("put")) {
                String page = sc.next();
                String result = cache.put(key, page);
                System.out.println("put(" + key + "," + page + ")" + (result.equals("updated") ? " updated" : result.startsWith("evicted") ? " " + result : ""));
            } else {
                System.out.println("get(" + key + ") -> " + cache.get(key));
            }
        }
        cache.display();
    }
}
