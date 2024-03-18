import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class ProgressTracker {
    private List<TrialEntry> entries;
    private JTextArea ptrackerTextArea;
    private LanguageManager languageManager;
    private JFrame ptrackerFrame;

    public ProgressTracker(LanguageManager languageManager) {
        this.entries = new ArrayList<>();
        this.languageManager = languageManager;
        this.ptrackerFrame = new JFrame(languageManager.getMessage("progress.title"));

    }

    public void addProgress(int trialNumber, int clicks, long seconds, int fails) {
        TrialEntry newEntry = new TrialEntry(trialNumber, clicks, seconds, fails);
        entries.add(newEntry);
    }

    public List<TrialEntry> getEntries() {
        return entries;
    }

    public void displayProgressTracker() {

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
            ptrackerTextArea.append(languageManager.getMessage("progress.trial") + entry.getTrialNumber() + " - " +
                    languageManager.getMessage("game.time")+ entry.getSeconds() + "s, " +
                    languageManager.getMessage("game.moves") + entry.getClicks() + ", " +
                    languageManager.getMessage("game.fails") + entry.getFails() +"\n");
        }
    }

    public void setLanguageManager (LanguageManager languageManager) {
        this.languageManager = languageManager;
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
