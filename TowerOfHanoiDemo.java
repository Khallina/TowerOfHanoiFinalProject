import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TowerOfHanoiDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Tower of Hanoi Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create Tower of Hanoi component
        TowerOfHanoiComponent towerOfHanoiComponent = new TowerOfHanoiComponent();

        // Create a button to start the demo
        JButton startButton = new JButton("Start Demo");
        startButton.addActionListener(e -> towerOfHanoiComponent.startDemo());

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(towerOfHanoiComponent, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);

        // Set frame properties
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class TowerOfHanoiComponent extends JComponent {

    private int numberOfDiscs = 3; // You can adjust the number of discs for the demonstration
    private TowerOfHanoi towerOfHanoi;

    public TowerOfHanoiComponent() {
        towerOfHanoi = new TowerOfHanoi(numberOfDiscs);
    }

    public void startDemo() {
        // Perform Tower of Hanoi steps with a delay for demonstration
        towerOfHanoi.solve();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the Tower of Hanoi
        towerOfHanoi.draw(g, getWidth(), getHeight());
    }
}

class TowerOfHanoi {

    private int numberOfDiscs;
    private int[] tower1, tower2, tower3;
    private int moveCount = 0;

    public TowerOfHanoi(int numberOfDiscs) {
        this.numberOfDiscs = numberOfDiscs;
        tower1 = new int[numberOfDiscs];
        tower2 = new int[numberOfDiscs];
        tower3 = new int[numberOfDiscs];

        // Initialize the first tower with discs
        for (int i = 0; i < numberOfDiscs; i++) {
            tower1[i] = numberOfDiscs - i;
        }
    }

    public void solve() {
        moveTower(numberOfDiscs, tower1, tower3, tower2);
    }

    private void moveTower(int n, int[] source, int[] target, int[] auxiliary) {
        if (n > 0) {
            moveTower(n - 1, source, auxiliary, target);
            moveDisk(source, target);
            moveTower(n - 1, auxiliary, target, source);
        }
    }

    private void moveDisk(int[] source, int[] target) {
        if (source[numberOfDiscs - 1] != 0) {
            target[moveCount++] = source[--numberOfDiscs];
        }
    }

    public void draw(Graphics g, int width, int height) {
        int towerWidth = width / 3;
        int towerHeight = height;

        drawTower(g, tower1, 0, width, towerWidth, towerHeight);
        drawTower(g, tower2, 1, width, towerWidth, towerHeight);
        drawTower(g, tower3, 2, width, towerWidth, towerHeight);
    }

    private void drawTower(Graphics g, int[] tower, int index, int width, int towerWidth, int towerHeight) {
        int x = index * (width / 3);
        int y = 20; // Adjust the y-coordinate for better visualization

        for (int i = 0; i < numberOfDiscs; i++) {
            int discWidth = tower[i] * towerWidth / numberOfDiscs;
            int discX = x + (towerWidth - discWidth) / 2;
            int discY = y + (numberOfDiscs - i - 1) * (towerHeight / numberOfDiscs);

            g.setColor(Color.BLUE); // Adjust color as needed
            g.fillRect(discX, discY, discWidth, 20); // Assuming a constant height for discs
        }
    }
}
