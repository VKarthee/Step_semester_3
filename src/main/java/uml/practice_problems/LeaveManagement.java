
import java.time.LocalDate;

abstract class Employee {

    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract int getMaxLeaveDays();

    public String getName() {
        return name;
    }
}

class FullTimeEmployee extends Employee {

    public FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    public int getMaxLeaveDays() {
        return 30;
    }
}

class PartTimeEmployee extends Employee {

    public PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    public int getMaxLeaveDays() {
        return 15;
    }
}

class LeaveRequest {

    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status = "Pending";

    public LeaveRequest(Employee employee,
            LocalDate startDate,
            LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void review(String decision) {
        if (!status.equals("Pending")) {
            System.out.println(
                    "Cannot change status: " + status
                    + " request cannot revert to Pending.");
            return;
        }

        if (!decision.equals("Approved")
                && !decision.equals("Rejected")) {
            System.out.println("Invalid decision.");
            return;
        }

        status = decision;

        System.out.println("Leave request for "
                + employee.getName() + " "
                + decision.toLowerCase() + ". Status: " + status);
    }

    public void display() {
        System.out.println(
                "Leave request submitted by "
                + employee.getName() + " for "
                + startDate + " to " + endDate
                + ". Status: " + status);
    }
}

class LeaveManager {

    public LeaveRequest submitRequest(Employee employee,
            LocalDate start,
            LocalDate end) {

        if (end.isBefore(start)) {
            System.out.println("Invalid leave dates.");
            return null;
        }

        long days = java.time.temporal.ChronoUnit.DAYS
                .between(start, end) + 1;

        if (days > employee.getMaxLeaveDays()) {
            System.out.println("Leave limit exceeded.");
            return null;
        }

        LeaveRequest request
                = new LeaveRequest(employee, start, end);

        request.display();

        return request;
    }

    public void reviewRequest(LeaveRequest request,
            String decision) {
        request.review(decision);
    }
}

public class LeaveManagement {

    public static void main(String[] args) {

        LeaveManager manager = new LeaveManager();

        Employee e1 = new FullTimeEmployee("John Doe");
        Employee e2 = new PartTimeEmployee("Jane Smith");

        LeaveRequest r1 = manager.submitRequest(
                e1,
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 12));

        manager.reviewRequest(r1, "Approved");

        LeaveRequest r2 = manager.submitRequest(
                e2,
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 5));

        manager.reviewRequest(r1, "Pending");
    }
}
