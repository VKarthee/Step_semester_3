
import java.util.*;

public class NetBalanceCounter {

    public static int countPeriods(int[] transactions, int k) {
        HashMap<Integer, Integer> prefixCount = new HashMap<>();

        prefixCount.put(0, 1);

        int sum = 0;
        int count = 0;

        for (int value : transactions) {
            sum += value;

            count += prefixCount.getOrDefault(sum - k, 0);

            prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions1 = {3, 4, -7, 1, 3, 3, 1, -4};
        int k1 = 7;

        int[] transactions2 = {1, 2, 3};
        int k2 = 10;

        System.out.println("Sample 1: " + countPeriods(transactions1, k1));
        System.out.println("Sample 2: " + countPeriods(transactions2, k2));
    }
}
