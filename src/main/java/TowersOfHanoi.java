import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class TowersOfHanoi extends JFrame implements MouseListener {
    private Peg[] pegs = new Peg[3];
    private int moves = 0;
    private long startTime;
    private JLabel movesLabel;
    private JLabel timerLabel;
    private Timer timer;
    private ColorThemeManager colorThemeManager;
    private SoundPlayer goodSound = new SuccessClick();
    private SoundPlayer badSound = new FailClick();
    private FailureTracker failureTracker;
    private FailureChart failureChart;
    private LanguageMenu languageMenu;
    private LanguageManager languageManager;
    private ProgressTracker progressTracker;
    private boolean textShown = false;

    public static void main(String[] args) {
        Instructions instructions;
        instructions = new Instructions();
        instructions.open();

        new TowersOfHanoi().setVisible(true);
    }

    public TowersOfHanoi() {
        initializeGame();
    }

    private void initializeGame() {
        Locale currentLocale = Locale.getDefault();
        this.languageManager = new LanguageManager(currentLocale, ResourceBundle.getBundle("messages", currentLocale));

        setSize(800, 800);
        setTitle(languageManager.getMessage("game.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);

        pegs[0] = new Peg(100, 100, 20, 250);
        pegs[1] = new Peg(350, 100, 20, 250);
        pegs[2] = new Peg(600, 100, 20, 250);

        this.initializeDisks();

        this.movesLabel = new JLabel(languageManager.getMessage("game.moves") + moves);
        this.movesLabel.setBounds(50, 50, 100, 20);
        this.add(movesLabel);

        this.timerLabel = new JLabel(languageManager.getMessage("game.time") + " 0s");
        this.timerLabel.setBounds(650, 50, 100, 20);
        this.add(timerLabel);

        this.startTime = System.currentTimeMillis();
        this.startTimer();

        JButton languageButton = new JButton("Language");
        languageButton.addActionListener(e -> openLanguageMenu());
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(languageButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        JPanel themePanel = new JPanel();
        String[] themeNames = {"Warm Colors", "Cool Colors", "Pastel Colors", "Rainbow"};
        for (String themeName : themeNames) {
            JButton button = new JButton(themeName);
            button.addActionListener(e -> changeColorTheme(themeName));
            themePanel.add(button);
        }
        this.add(themePanel, BorderLayout.NORTH);

        failureTracker = new FailureTracker(0);
        failureChart = new FailureChart(10, 100, languageManager);
        add(failureChart, BorderLayout.CENTER);
        setLayout(new GridLayout(0, 1));
        add(failureChart);

        languageMenu = new LanguageMenu(this, languageManager);

        progressTracker = new ProgressTracker();
        JButton progressTrackerButton = new JButton("Progress Tracker");
        progressTrackerButton.addActionListener(e -> progressTracker.displayProgressTracker());
        JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        progressPanel.add(progressTrackerButton);
        add(progressPanel, BorderLayout.NORTH);
    }

    private void initializeDisks() {
        colorThemeManager = new ColorThemeManager();
        Color[] colors = colorThemeManager.getCurrentColorTheme();
        for (int i = 5; i >= 0; i--) {
            this.pegs[0].addDisk(new Disk(i + 1, colors[i], 0, 0, 60 + i * 10, 20));
        }
    }
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                timerLabel.setText(languageManager.getMessage("game.time") + elapsedTime + "s");
                repaint();
                // Check if 10 seconds have passed
                if (elapsedTime >= 10 && !textShown) {
                    //timer.cancel(); // Stop the timer
                    openHelperTool(); // Open the HelperTool window
                    textShown = true;
                }
            }
        }, 0L, 1000L);
    }
    private void changeColorTheme(String themeName) {
        colorThemeManager.setCurrentThemeIndex(getThemeIndex(themeName));
        Color[] newColors = colorThemeManager.getCurrentColorTheme();
        for (Peg peg : pegs) {
            for (int i = 0; i < peg.getDisks().size(); i++) {
                Disk disk = peg.getDisks().get(i);
                if (i < newColors.length) {
                    disk.setColor(newColors[i]);
                }
            }
        }
        repaint();
    }

    private int getThemeIndex(String themeName) {
        switch (themeName) {
            case "Warm Colors":
                return 0;
            case "Cool Colors":
                return 1;
            case "Pastel Colors":
                return 2;
            case "Rainbow":
                return 3;
            default:
                return 3; // Default to rainbow theme
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Peg peg : pegs) {
            peg.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (int i = 0; i < pegs.length; i++) {
            Peg peg = pegs[i];
            Disk topDisk = peg.peekTopDisk();

            final int index = i;

            if (topDisk != null && topDisk.contains(x, y)) {
                for (int j = 1; j < pegs.length; j++) {
                    int nextPegIndex = (i + j) % pegs.length;
                    Peg nextPeg = pegs[nextPegIndex];
                    if (nextPeg.isEmpty() || topDisk.getSize() < nextPeg.peekTopDisk().getSize()) {
                        Animation animation = new Animation(topDisk, peg, nextPeg, 5);
                        Timer animationTimer = new Timer();
                        animationTimer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                if (!animation.isFinished()) {
                                    animation.update();
                                    repaint();
                                } else {
                                    peg.removeTopDisk();
                                    nextPeg.addDisk(topDisk);
                                    moves++;
                                    movesLabel.setText(languageManager.getMessage("game.moves") + moves);
                                    animationTimer.cancel();
                                    repaint();
                                    if (nextPegIndex == 2 && nextPeg.getDiskCount() == 6) {
                                        timer.cancel();
                                        progressTracker.addProgress(index, moves, (System.currentTimeMillis() - startTime) / 1000, failureTracker.getFails());
                                        JOptionPane.showMessageDialog(null,
                                                languageManager.getMessage("game.congratulations") + "\n" +
                                                        languageManager.getMessage("game.fails") + failureTracker.getFails() + "\n" +
                                                        languageManager.getMessage("game.time") + (System.currentTimeMillis() - startTime) / 1000 + "\n" +
                                                        languageManager.getMessage("game.moves")+ moves);
                                    }
                                    goodSound.play();
                                    //failureChart.updateChart(failureTracker.getFails()); // Update FailureChart
                                    return;

                                }
                            }
                        }, 0, 1000 / 100);
                        return;
                    }
                }
                badSound.play();
                failureTracker.addFail();
                failureChart.updateChart(failureTracker.getFails());
                return;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    private void openHelperTool() {
        HelperTool helpertool = new HelperTool();
        helpertool.open();
    }
    private void openLanguageMenu() {
        languageMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateLanguage();
            }
        });
        languageMenu.pack();
        languageMenu.setLocationRelativeTo(null);
        languageMenu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        languageMenu.setVisible(true);
    }

    public void updateLanguage() {
        setTitle(languageManager.getMessage("game.title"));
        movesLabel.setText(languageManager.getMessage("game.moves") + moves);
        this.failureChart.updateLanguage(failureTracker.getFails());
    }
}
