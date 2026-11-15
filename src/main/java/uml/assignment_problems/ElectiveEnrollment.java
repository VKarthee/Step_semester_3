
import java.util.*;

abstract class Student {

    protected String name;
    protected int creditLimit;
    protected int creditsUsed;

    Student(String name, int creditLimit) {
        this.name = name;
        this.creditLimit = creditLimit;
        this.creditsUsed = 0;
    }

    public String getName() {
        return name;
    }

    public boolean canEnroll(int credits) {
        return creditsUsed + credits <= creditLimit;
    }

    public void addCredits(int credits) {
        creditsUsed += credits;
    }

    public void removeCredits(int credits) {
        creditsUsed -= credits;
    }

    public void displayDetails() {
        System.out.println(name + " | Credits: "
                + creditsUsed + "/" + creditLimit);
    }
}

class RegularStudent extends Student {

    RegularStudent(String name) {
        super(name, 24);
    }
}

class HonorsStudent extends Student {

    HonorsStudent(String name) {
        super(name, 28);
    }
}

class ExchangeStudent extends Student {

    ExchangeStudent(String name) {
        super(name, 20);
    }
}

class Elective {

    private String courseName;
    private int credits;
    private int capacity;

    private ArrayList<Student> enrolledStudents;
    private Queue<Student> waitlist;

    Elective(String courseName, int credits, int capacity) {
        this.courseName = courseName;
        this.credits = credits;
        this.capacity = capacity;

        enrolledStudents = new ArrayList<>();
        waitlist = new LinkedList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    public boolean contains(Student student) {
        return enrolledStudents.contains(student)
                || waitlist.contains(student);
    }

    public boolean hasAvailableSeat() {
        return enrolledStudents.size() < capacity;
    }

    public void addStudent(Student student) {
        enrolledStudents.add(student);
        student.addCredits(credits);
    }

    public void addToWaitlist(Student student) {
        waitlist.offer(student);
    }

    public boolean dropStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            student.removeCredits(credits);
            return true;
        }
        return false;
    }

    public void promoteNext() {
        while (hasAvailableSeat() && !waitlist.isEmpty()) {

            Student nextStudent = waitlist.poll();

            if (nextStudent.canEnroll(credits)) {
                addStudent(nextStudent);

                System.out.println(nextStudent.getName()
                        + " has been promoted from the waitlist.");
            } else {
                System.out.println(nextStudent.getName()
                        + " cannot be promoted due to credit limit.");
            }
        }
    }

    public void displayStatus() {
        System.out.println("\nElective: " + courseName);
        System.out.println("Credits: " + credits);
        System.out.println("Available Capacity: "
                + (capacity - enrolledStudents.size()));

        System.out.println("\nEnrolled Students:");

        for (Student student : enrolledStudents) {
            System.out.println("- " + student.getName());
        }

        System.out.println("\nWaitlisted Students:");

        for (Student student : waitlist) {
            System.out.println("- " + student.getName());
        }
    }
}

class EnrollmentService {

    public void enroll(Student student, Elective elective) {

        if (elective.contains(student)) {
            System.out.println(student.getName()
                    + " is already enrolled or waitlisted.");
            return;
        }

        if (!student.canEnroll(elective.getCredits())) {
            System.out.println(student.getName()
                    + " cannot enroll. Credit limit exceeded.");
            return;
        }

        if (elective.hasAvailableSeat()) {
            elective.addStudent(student);

            System.out.println(student.getName()
                    + " successfully enrolled in "
                    + elective.getCourseName());
        } else {
            elective.addToWaitlist(student);

            System.out.println(student.getName()
                    + " added to waitlist for "
                    + elective.getCourseName());
        }
    }

    public void drop(Student student, Elective elective) {

        if (!elective.isEnrolled(student)) {
            System.out.println(student.getName()
                    + " is not enrolled in "
                    + elective.getCourseName());
            return;
        }

        elective.dropStudent(student);

        System.out.println(student.getName()
                + " dropped " + elective.getCourseName());

        elective.promoteNext();
    }
}

public class ElectiveEnrollment {

    public static void main(String[] args) {

        EnrollmentService service = new EnrollmentService();

        Student s1 = new RegularStudent("Asha");
        Student s2 = new HonorsStudent("Ravi");
        Student s3 = new ExchangeStudent("Neha");
        Student s4 = new RegularStudent("Kiran");

        Elective elective
                = new Elective("Artificial Intelligence", 4, 2);

        System.out.println("----- ELECTIVE ENROLLMENT -----");

        service.enroll(s1, elective);
        service.enroll(s2, elective);
        service.enroll(s3, elective);
        service.enroll(s4, elective);

        elective.displayStatus();

        System.out.println("\n----- DROP PROCESS -----");

        service.drop(s2, elective);

        elective.displayStatus();

        System.out.println("\n----- STUDENT CREDIT DETAILS -----");

        s1.displayDetails();
        s2.displayDetails();
        s3.displayDetails();
        s4.displayDetails();
    }
}
