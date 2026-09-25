
class RaceEntry {

    private String bibNumber;

    RaceEntry(String bibNumber) {
        this.bibNumber = bibNumber;
    }

    public void announce() {
        System.out.println("Race Entry: " + bibNumber);
    }
}

class RunnerEntry extends RaceEntry {

    RunnerEntry(String bibNumber) {
        super(bibNumber);
    }

    @Override
    public void announce() {
        System.out.println("Runner Entry");
    }
}

class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    RelayTeamEntry(String bibNumber, int teamSize) {
        super(bibNumber);
        this.teamSize = teamSize;
    }

    @Override
    public void announce() {
        System.out.println("Relay Team Entry");
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class AnnouncerBoard {

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder board = new StringBuilder();

        for (RaceEntry entry : entries) {
            entry.announce();

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                board.append("Relay Team - Size: " + relay.getTeamSize() + "\n");
            } else {
                board.append("Regular Race Entry\n");
            }
        }

        return board.toString();
    }

    public static void main(String[] args) {
        RaceEntry[] entries = {
            new RunnerEntry("R001"),
            new RelayTeamEntry("R002", 4),
            new RunnerEntry("R003")
        };

        System.out.println(announceAll(entries));
    }
}
