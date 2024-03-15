import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelperTool extends JFrame {
    public HelperTool() {
        setTitle("Feeling Stuck?");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("Here are Some Hints!\n\n" +
                "\n\n" +
                "1. Always start moving the disks from the smallest to the largest. \n" +
                "2. As you're moving disks, think about the bottom disk in each tower.\n" +
                "3. If you're moving a tower of size N, think about how you can break it down into smaller towers of size N-1. \n" +
                "4.  Like any puzzle, solving Tower of Hanoi requires practice and patience. Keep trying and learn from each attempt.\n\n" +
                "Click the button to close the hints!");

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        JButton closeButton = new JButton("OK");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the dialog
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Instructions instructions = new Instructions();
            instructions.setVisible(true);
        });
    }

    public void open() {
        setVisible(true);
    }
}