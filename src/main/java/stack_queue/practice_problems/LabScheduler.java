
import java.util.*;

class Job {

    String name;
    int remaining;

    Job(String n, int t) {
        name = n;
        remaining = t;
    }
}

class LabScheduler {

    Queue<Job> queue = new LinkedList<>();

    void addJob(String n, int t) {
        queue.add(new Job(n, t));
    }

    void schedule(int quantum) {
        int time = 0;
        StringBuilder slices = new StringBuilder();
        StringBuilder finish = new StringBuilder();
        while (!queue.isEmpty()) {
            Job j = queue.poll();
            int start = time;
            int run = Math.min(quantum, j.remaining);
            time += run;
            j.remaining -= run;
            slices.append(j.name).append("(").append(start).append("-").append(time).append(") ");
            if (j.remaining > 0) {
                queue.add(j); 
            }else {
                finish.append(j.name).append(" ").append(time).append(", ");
            }
        }
        System.out.println(slices.toString().trim());
        System.out.println("finish " + finish.toString().replaceAll(", $", ""));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        int n = sc.nextInt();
        LabScheduler scheduler = new LabScheduler();
        for (int i = 0; i < n; i++) {
            scheduler.addJob(sc.next(), sc.nextInt());
        }
        scheduler.schedule(q);
    }
}
