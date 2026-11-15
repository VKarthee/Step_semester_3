
import java.util.Stack;

public class PriceStreak {

    static int[] priceStreak(int[] price) {
        int n = price.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && price[stack.peek()] <= price[i]) {
                stack.pop();
            }

            result[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] price = {100, 80, 60, 70, 60, 75, 85};
        int[] result = priceStreak(price);

        for (int value : result) {
            System.out.print(value + " ");
        }

        System.out.println();

        int[] price2 = {10, 20, 30};
        int[] result2 = priceStreak(price2);

        for (int value : result2) {
            System.out.print(value + " ");
        }
    }
}
