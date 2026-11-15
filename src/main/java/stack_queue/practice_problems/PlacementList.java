
import java.util.*;

class Candidate {

    String name;
    int[] scores = new int[6];
    int total;
    int bestGain = 0, start = 1, end = 1;
    int order;

    Candidate(String n, int[] s, int o) {
        name = n;
        scores = s;
        order = o;
        for (int x : s) {
            total += x;
        }
        int current = 0, begin = 1;
        for (int i = 1; i < 6; i++) {
            int change = s[i] - s[i - 1];
            if (current <= 0) {
                current = change;
                begin = i;
            } else {
                current += change;
            }
            if (current > bestGain) {
                bestGain = current;
                start = begin;
                end = i + 1;
            }
        }
    }
}

class PlacementList {

    static void mergeSort(Candidate[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int m = (l + r) / 2;
        mergeSort(a, l, m);
        mergeSort(a, m + 1, r);
        Candidate[] temp = new Candidate[r - l + 1];
        int i = l, j = m + 1, k = 0;
        while (i <= m && j <= r) {
            if (a[i].total >= a[j].total) {
                temp[k++] = a[i++]; 
            }else {
                temp[k++] = a[j++];
            }
        }
        while (i <= m) {
            temp[k++] = a[i++];
        }
        while (j <= r) {
            temp[k++] = a[j++];
        }
        for (i = 0; i < temp.length; i++) {
            a[l + i] = temp[i];
        }
    }

    static int cutoff(Candidate[] a, int x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (a[m].total >= x) {
                l = m + 1; 
            }else {
                r = m;
            }
        }
        return l;
    }

    static void pair(Candidate[] a, int target) {
        int l = 0, r = a.length - 1;
        while (l < r) {
            int sum = a[l].total + a[r].total;
            if (sum == target) {
                System.out.println("PAIR " + target + ": " + a[l].name + " + " + a[r].name);
                return;
            }
            if (sum > target) {
                r--; 
            }else {
                l++;
            }
        }
        System.out.println("PAIR " + target + ": no pair");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Candidate[] a = new Candidate[n];
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int[] s = new int[6];
            for (int j = 0; j < 6; j++) {
                s[j] = sc.nextInt();
            }
            a[i] = new Candidate(name, s, i);
        }
        mergeSort(a, 0, n - 1);
        System.out.println("Shortlist:");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". " + a[i].name + " " + a[i].total + " | best climb +" + a[i].bestGain + " (test " + a[i].start + " to " + a[i].end + ")");
        }
        while (sc.hasNext()) {
            String cmd = sc.next();
            int x = sc.nextInt();
            if (cmd.equalsIgnoreCase("CUTOFF")) {
                System.out.println("CUTOFF " + x + ": " + cutoff(a, x) + " candidates"); 
            }else if (cmd.equalsIgnoreCase("PAIR")) {
                pair(a, x);
            }
        }
    }
}
