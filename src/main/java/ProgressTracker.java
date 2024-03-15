import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class ProgressTracker {
    private List<TrialEntry> entries;
    private JTextArea ptrackerTextArea;

    public ProgressTracker() {
        this.entries = new ArrayList<>();
    }

    public void addProgress(int trialNumber, int clicks, long seconds, int fails) {
        TrialEntry newEntry = new TrialEntry(trialNumber, clicks, seconds, fails);
        entries.add(newEntry);
    }

    public List<TrialEntry> getEntries() {
        return entries;
    }

    public void displayProgressTracker() {
        JFrame ptrackerFrame = new JFrame("Progress Tracker");
        ptrackerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ptrackerFrame.setSize(400, 300);

        ptrackerTextArea = new JTextArea();
        ptrackerTextArea.setEditable(false);
        updateProgressTracker();

        JScrollPane scrollPane = new JScrollPane(ptrackerTextArea);
        ptrackerFrame.add(scrollPane);

        ptrackerFrame.setLocationRelativeTo(null); // Center the frame
        ptrackerFrame.setVisible(true);
    }

    private void updateProgressTracker() {
        ptrackerTextArea.setText("");
        for (TrialEntry entry : this.getEntries()) {
            ptrackerTextArea.append("Trial" + entry.getTrialNumber() + " - Time: " + entry.getSeconds() + "s, Moves: " + entry.getClicks() + ", Fails: "+ entry.getFails() +"\n");
        }
    }



    public class TrialEntry {
        private int trialNumber;
        private int clicks;
        private long seconds;

        private int fails;

        public TrialEntry(int trialNumber, int clicks, long seconds, int fails) {
            this.trialNumber = trialNumber;
            this.clicks = clicks;
            this.seconds = seconds;
            this.fails = fails;
        }

        public int getTrialNumber() {
            return trialNumber;
        }

        public int getFails() {
            return fails;
        }

        public int getClicks() {
            return clicks;
        }

        public long getSeconds() {
            return seconds;
        }
    }
}
