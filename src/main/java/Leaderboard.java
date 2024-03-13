import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {
    private List<ScoreEntry> entries;

    public Leaderboard() {
        this.entries = new ArrayList<>();
    }

    public void addScore(String playerName, long timeTaken, int moves) {
        ScoreEntry newEntry = new ScoreEntry(playerName, timeTaken, moves);
        entries.add(newEntry);
        // Sort entries based on time taken
        Collections.sort(entries, Comparator.comparingLong(ScoreEntry::getTimeTaken));
    }

    public List<ScoreEntry> getEntries() {
        return entries;
    }

    public void displayLeaderboard() {
        System.out.println("Leaderboard:");
        for (int i = 0; i < Math.min(entries.size(), 10); i++) {
            ScoreEntry entry = entries.get(i);
            System.out.println((i + 1) + ". " + entry.getPlayerName() + " - Time: " + entry.getTimeTaken() + "s, Moves: " + entry.getMoves());
        }
    }

    public class ScoreEntry {
        private String playerName;
        private long timeTaken;
        private int moves;

        public ScoreEntry(String playerName, long timeTaken, int moves) {
            this.playerName = playerName;
            this.timeTaken = timeTaken;
            this.moves = moves;
        }

        public String getPlayerName() {
            return playerName;
        }

        public long getTimeTaken() {
            return timeTaken;
        }

        public int getMoves() {
            return moves;
        }
    }
}
