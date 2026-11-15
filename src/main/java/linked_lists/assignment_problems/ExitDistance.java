
import java.util.*;

public class ExitDistance {

    static int[][] exitDistance(char[][] plan) {
        int rows = plan.length;
        int cols = plan[0].length;

        int[][] distance = new int[rows][cols];
        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < rows; i++) {
            Arrays.fill(distance[i], -1);

            for (int j = 0; j < cols; j++) {
                if (plan[i][j] == 'E') {
                    distance[i][j] = 0;
                    queue.offer(i * cols + j);
                }
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int cell = queue.poll();

            int r = cell / cols;
            int c = cell % cols;

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && plan[nr][nc] == '.' && distance[nr][nc] == -1) {
                    distance[nr][nc] = distance[r][c] + 1;
                    queue.offer(nr * cols + nc);
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        char[][] plan = {
            {'.', '#', '.', 'E'},
            {'.', '.', '.', '#'},
            {'E', '#', '.', '.'}
        };

        int[][] result = exitDistance(plan);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}
