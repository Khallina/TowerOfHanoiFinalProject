import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

 public class TowersOfHanoi extends JFrame implements MouseListener {
    private Peg[] pegs = new Peg[3];
    private int moves = 0;
    private long startTime;
    private JLabel movesLabel;
    private JLabel timerLabel;
    private Timer timer;

     private SoundPlayer goodSound = new SuccessClick();
    private SoundPlayer badSound = new FailClick();

     private final Color[][] colorThemes = {
             // Warm colors theme
             {new Color(255, 102, 102), new Color(255, 153, 153), new Color(255, 204, 153),
                     new Color(255, 255, 153), new Color(255, 204, 153), new Color(255, 153, 153)},

             // Cool colors theme
             {new Color(102, 178, 255), new Color(153, 204, 255), new Color(153, 255, 255),
                     new Color(204, 255, 255), new Color(153, 255, 204), new Color(102, 255, 178)},

             // Pastel colors theme
             {new Color(255, 204, 255), new Color(255, 204, 204), new Color(255, 255, 204),
                     new Color(204, 255, 204), new Color(204, 255, 255), new Color(204, 204, 255)},

             // Rainbow theme
             {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA}
     };
     private int currentThemeIndex = 3;
    public static void main(String[] args) {
        new TowersOfHanoi().setVisible(true);
    }
    public TowersOfHanoi() {
        initializeGame();
    }

    private void initializeGame() {

        LanguageManager.initialize();
        setSize(800, 600);
        setTitle(LanguageManager.getMessage("game.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);

        pegs[0] = new Peg(100, 100, 20, 250);
        pegs[1] = new Peg(350, 100, 20, 250);
        pegs[2] = new Peg(600, 100, 20, 250);

        // Initialize and add disks to the first peg
        //for (int i = 5; i >= 0; i--) {
            //Color color = new Color((int) (Math.random() * 0x1000000));
            //pegs[0].addDisk(new Disk(i + 1, color, 0, 0, 60 + i * 10, 20));
        //}
        this.initializeDisks(colorThemes[currentThemeIndex]);

        this.movesLabel = new JLabel(LanguageManager.getMessage("game.moves") + moves);
        this.movesLabel.setBounds(50, 50, 100, 20);
        this.add(movesLabel);

        this.timerLabel = new JLabel(LanguageManager.getMessage("game.time") + " 0s");
        this.timerLabel.setBounds(650, 50, 100, 20);
        this.add(timerLabel);

        this.startTime = System.currentTimeMillis();
        this.startTimer();
        //repaint();

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
        this.add(themePanel, BorderLayout.SOUTH);
    }

    private void initializeDisks(Color[] colors) {
        for (int i = 5; i >= 0; i--) {
            this.pegs[0].addDisk(new Disk(i + 1, colors[i], 0, 0, 60 + i * 10, 20));
        }
    }
   /* private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                timerLabel.setText(LanguageManager.getMessage("game.time") + (elapsedTime / 1000) + "s");
                repaint();
            }
        }, 0, 1000);
    }*/
   private void startTimer() {
       timer = new Timer();
       timer.scheduleAtFixedRate(new TimerTask() {
           public void run() {
               long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
               timerLabel.setText("Time: " + elapsedTime + "s");
               repaint();
           }
       }, 0L, 1000L);
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
     private void changeColorTheme(String themeName) {
         switch (themeName) {
             case "Warm Colors":
                 currentThemeIndex = 0;
                 break;
             case "Cool Colors":
                 currentThemeIndex = 1;
                 break;
             case "Pastel Colors":
                 currentThemeIndex = 2;
                 break;
             case "Rainbow":
                 currentThemeIndex = 3;
                 break;
             default:
                 // Default to rainbow theme if unknown theme name is provided
                 currentThemeIndex = 3;
                 break;
         }

         // Update colors of all disks based on the new theme
         Color[] newColors = colorThemes[currentThemeIndex];
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
     @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (int i = 0; i < pegs.length; i++) {
            Peg peg = pegs[i];
            Disk topDisk = peg.peekTopDisk();
            if (topDisk != null && topDisk.contains(x, y)) {
                for (int j = 1; j < pegs.length; j++) {
                    int nextPegIndex = (i + j) % pegs.length;
                    Peg nextPeg = pegs[nextPegIndex];
                    if (nextPeg.isEmpty() || topDisk.getSize() < nextPeg.peekTopDisk().getSize()) {
                        peg.removeTopDisk();
                        nextPeg.addDisk(topDisk);
                        moves++;
                        movesLabel.setText(LanguageManager.getMessage("game.moves") + moves);
                        repaint();
                        if (nextPegIndex == 2 && nextPeg.getDiskCount() == 6) {
                            timer.cancel();
                            JOptionPane.showMessageDialog(null,
                                    LanguageManager.getMessage("game.congratulations") + " " +
                                            (System.currentTimeMillis() - startTime) / 1000 + " seconds with " + moves + " moves.");
                        }
                         goodSound.play();
                        return;
                    }
                }
                 badSound.play();
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

     private void openLanguageMenu() {
         LanguageMenu languageMenu = new LanguageMenu(this);
         languageMenu.addWindowListener(new WindowAdapter() {
             @Override
             public void windowClosed(WindowEvent e) {
                 updateLanguage();
             }
         });
         languageMenu.pack();
         languageMenu.setLocationRelativeTo(null); // Center the dialog
         languageMenu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
         languageMenu.setVisible(true);
     }

     public void updateLanguage() {
         setTitle(LanguageManager.getMessage("game.title"));
         movesLabel.setText(LanguageManager.getMessage("game.moves") + moves);
     }
}