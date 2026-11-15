
public class LongestStreak {

    public static int[] longestStreak(int[] costs, int budget) {
        int left = 0;
        int sum = 0;
        int maxLength = 0;
        int startIndex = -1;

        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];

            while (sum > budget && left <= right) {
                sum -= costs[left];
                left++;
            }

            int length = right - left + 1;

            if (length > maxLength) {
                maxLength = length;
                startIndex = left;
            }
        }

        return new int[]{maxLength, startIndex};
    }

    public static void main(String[] args) {
        int[] costs1 = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        int budget1 = 8;

        int[] costs2 = {9, 10};
        int budget2 = 8;

        int[] result1 = longestStreak(costs1, budget1);
        int[] result2 = longestStreak(costs2, budget2);

        System.out.println("Sample 1: (" + result1[0] + ", " + result1[1] + ")");
        System.out.println("Sample 2: (" + result2[0] + ", " + result2[1] + ")");
    }
}
