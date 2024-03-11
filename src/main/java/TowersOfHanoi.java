import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class TowersOfHanoi extends JFrame implements MouseListener {
    private Peg[] pegs = new Peg[3];
    private int moves = 0;
    private long startTime;
    private JLabel movesLabel;
    private JLabel timerLabel;
    private Timer timer;
    //private SoundPlayer goodSound = new SuccessClick();
    //private SoundPlayer badSound = new FailClick();
    public static void main(String[] args) {
        new TowersOfHanoi().setVisible(true);
    }
    public TowersOfHanoi() {
        initializeGame();
    }
    private void initializeGame() {
        setSize(800, 600);
        setTitle("Towers of Hanoi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);

        pegs[0] = new Peg(100, 100, 20, 250);
        pegs[1] = new Peg(350, 100, 20, 250);
        pegs[2] = new Peg(600, 100, 20, 250);

        // Initialize and add disks to the first peg
        for (int i = 5; i >= 0; i--) {
            Color color = new Color((int)(Math.random() * 0x1000000));
            pegs[0].addDisk(new Disk(i + 1, color, 0, 0, 60 + i * 10, 20));
        }

        movesLabel = new JLabel("Moves: " + moves);
        movesLabel.setBounds(50, 50, 100, 20);
        add(movesLabel);

        timerLabel = new JLabel("Time: 0s");
        timerLabel.setBounds(650, 50, 100, 20);
        add(timerLabel);

        startTime = System.currentTimeMillis();
        startTimer();
        //repaint();
    }
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                timerLabel.setText("Time: " + (elapsedTime / 1000) + "s");
                repaint();
            }
        }, 0, 1000);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
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
            if (topDisk != null && topDisk.contains(x, y)) {
                for (int j = 1; j < pegs.length; j++) {
                    int nextPegIndex = (i + j) % pegs.length;
                    Peg nextPeg = pegs[nextPegIndex];
                    if (nextPeg.isEmpty() || topDisk.getSize() < nextPeg.peekTopDisk().getSize()) {
                        peg.removeTopDisk();
                        nextPeg.addDisk(topDisk);
                        moves++;
                        movesLabel.setText("Moves: " + moves);
                        repaint();
                        if (nextPegIndex == 2 && nextPeg.getDiskCount() == 6) {
                            timer.cancel();
                            JOptionPane.showMessageDialog(null, "Congratulations! You completed the game in " + (System.currentTimeMillis() - startTime) / 1000 + " seconds with " + moves + " moves.");
                        }
                       // goodSound.play();
                        return;
                    }
                }
               // badSound.play();
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
}