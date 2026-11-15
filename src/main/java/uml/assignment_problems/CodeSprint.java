
import java.util.*;

interface ScoringRule {

    double calculateScore(double idea, double execution,
            double presentation);
}

class InnovationScoring implements ScoringRule {

    public double calculateScore(double idea, double execution,
            double presentation) {
        return idea * 0.5 + execution * 0.3 + presentation * 0.2;
    }
}

class OpenScoring implements ScoringRule {

    public double calculateScore(double idea, double execution,
            double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Track {

    String name;
    ScoringRule rule;

    public Track(String name, ScoringRule rule) {
        this.name = name;
        this.rule = rule;
    }
}

class Student {

    String name;

    public Student(String name) {
        this.name = name;
    }
}

class Team {

    String name;
    List<Student> members;
    Track track;
    Project project;

    public Team(String name, List<Student> members, Track track) {
        this.name = name;
        this.members = members;
        this.track = track;
    }
}

class Project {

    String name;
    Team team;
    double finalScore;
    boolean scored = false;

    public Project(String name, Team team) {
        this.name = name;
        this.team = team;
    }
}

class Hackathon {

    private List<Team> teams = new ArrayList<>();
    private Set<String> studentNames = new HashSet<>();
    private boolean published = false;

    public void registerTeam(Team team) {
        if (published) {
            System.out.println("Registration closed.");
            return;
        }

        int size = team.members.size();

        if (size < 2 || size > 4) {
            System.out.println(
                    "Registration failed: A team must have 2 to 4 members.");
            return;
        }

        for (Student s : team.members) {
            if (studentNames.contains(s.name)) {
                System.out.println(
                        "Registration failed: " + s.name
                        + " already belongs to a team.");
                return;
            }
        }

        teams.add(team);

        for (Student s : team.members) {
            studentNames.add(s.name);
        }

        System.out.println("Team " + team.name
                + " registered (" + size
                + " members, " + team.track.name + " track).");
    }

    public void submitProject(Team team, String projectName) {
        if (published || !teams.contains(team)) {
            System.out.println("Project submission rejected.");
            return;
        }

        if (team.project != null) {
            System.out.println("Team has already submitted a project.");
            return;
        }

        team.project = new Project(projectName, team);

        System.out.println("Project '" + projectName
                + "' submitted by " + team.name + ".");
    }

    public void recordScore(Project project,
            double idea, double execution,
            double presentation) {

        if (published) {
            System.out.println(
                    "Rescore rejected: Results have already been published.");
            return;
        }

        if (project == null || project.team.project != project) {
            System.out.println("Invalid project.");
            return;
        }

        if (idea < 0 || idea > 10
                || execution < 0 || execution > 10
                || presentation < 0 || presentation > 10) {
            System.out.println("Invalid ratings.");
            return;
        }

        project.finalScore = project.team.track.rule.calculateScore(
                idea, execution, presentation);

        project.scored = true;

        System.out.println("Score recorded for '"
                + project.name + "'.");
    }

    public void publishResults() {
        published = true;

        System.out.println("Results published.");

        for (Team team : teams) {
            if (team.project != null && team.project.scored) {
                System.out.printf("Final score: %.2f%n",
                        team.project.finalScore);
            }
        }
    }
}

public class CodeSprint {

    public static void main(String[] args) {

        Hackathon hackathon = new Hackathon();

        Track innovation = new Track(
                "Innovation", new InnovationScoring());

        Track open = new Track(
                "Open", new OpenScoring());

        Team byteBusters = new Team(
                "ByteBusters",
                Arrays.asList(
                        new Student("Asha"),
                        new Student("Ravi"),
                        new Student("Neha")),
                innovation);

        Team soloCoder = new Team(
                "SoloCoder",
                Arrays.asList(new Student("Kiran")),
                open);

        hackathon.registerTeam(byteBusters);
        hackathon.registerTeam(soloCoder);

        hackathon.submitProject(byteBusters, "SmartAttend");

        hackathon.recordScore(
                byteBusters.project, 8, 7, 9);

        hackathon.publishResults();

        hackathon.recordScore(
                byteBusters.project, 10, 7, 9);
    }
}
