
public class ProductPriceFinder {

    public static int lowerBound(int[] prices, int target) {
        int low = 0;
        int high = prices.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (prices[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    public static int upperBound(int[] prices, int target) {
        int low = 0;
        int high = prices.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (prices[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    public static void main(String[] args) {
        int[] prices = {100, 150, 150, 200, 300, 450};

        int low = 150;
        int high = 300;

        int lower = lowerBound(prices, low);
        int upper = upperBound(prices, high);

        System.out.println("Lower bound index: " + lower);
        System.out.println("Upper bound index: " + upper);
        System.out.println("Count: " + (upper - lower));
    }
}
