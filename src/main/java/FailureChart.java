import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class FailureChart extends JPanel {
    private int[] failureCounts;
    private int maxWidth;
    private int maxHeight;
    private final Color barColor = new Color(255, 69, 69); // Red color for bars
    private final Color borderColor = Color.BLACK; // Black color for bar borders
    private JLabel titleLabel;
    private JTextField failCountField;
    private LanguageManager languageManager;

    public FailureChart(int maxWidth, int maxHeight, LanguageManager languageManager) {
        this.languageManager = languageManager;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.failureCounts = new int[maxWidth];

        // Initialize title label
        titleLabel = new JLabel(languageManager.getMessage("game.failchart"));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Initialize fail count field
        failCountField = new JTextField(5);
        failCountField.setEditable(false);
        failCountField.setHorizontalAlignment(SwingConstants.CENTER);
        failCountField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set layout
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(failCountField, BorderLayout.SOUTH);
    }

    public void updateChart(int newFailureCount) {
        // Shift the failure counts to the left
        for (int i = 0; i < maxWidth - 1; i++) {
            failureCounts[i] = failureCounts[i + 1];
        }
        failureCounts[maxWidth - 1] = newFailureCount;

        // Update fail count field
        failCountField.setText(languageManager.getMessage("game.fails") + newFailureCount);

        repaint();
    }

    public void updateLanguage(int newFailureCount) {
        // Replace the oldest failure count with the new value
        failureCounts[0] = newFailureCount;
        
        // Update fail count field

        failCountField.setText(languageManager.getMessage("game.fails") + newFailureCount);
        titleLabel.setText(languageManager.getMessage("game.failchart") + ": " + newFailureCount);
    
        repaint();
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int barWidth = getWidth() / maxWidth;
        int x = 0;

        for (int i = 0; i < maxWidth; i++) {
            // Calculate bar height based on a fraction of the panel's height
            int barHeight = (int) (((double) failureCounts[i] / maxHeight) * getHeight() * 10); // Adjust the multiplier (1.5) for taller bars

            int y = getHeight() - barHeight;

            // Draw the bar
            g2d.setColor(barColor);
            g2d.fillRect(x, y, barWidth, barHeight);

            // Draw the border
            g2d.setColor(borderColor);
            g2d.drawRect(x, y, barWidth, barHeight);

            x += barWidth;
        }

        g2d.dispose(); // Dispose the graphics object
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Locale currentLocale = Locale.getDefault();// init language manager
            LanguageManager languageManager = new LanguageManager(currentLocale, ResourceBundle.getBundle("messages", currentLocale));
            JFrame frame = new JFrame("Failure Test");
            FailureChart failureChart = new FailureChart(20, 10,languageManager); // Adjust the parameters as needed
            frame.add(failureChart);
            frame.setSize(400, 250); // Increased height to accommodate the new components
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // Example of updating the failure count
            FailureTracker failureTracker = new FailureTracker(0); // Initial value
            Timer timer = new Timer(1000, e -> {
                failureTracker.addFail(); // Simulate failure increment
                failureChart.updateChart(failureTracker.getFails()); // Update the chart
            });
            timer.start();
        });
    }
}
