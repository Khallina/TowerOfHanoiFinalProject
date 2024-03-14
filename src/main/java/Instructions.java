import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JFrame {
    public Instructions() {
        setTitle("Tower of Hanoi Instructions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("Tower of Hanoi Game\n\n" +
                "Instructions:\n\n" +
                "1. Move all discs from one peg to another.\n" +
                "2. Only one disc can be moved at a time.\n" +
                "3. A disc can only be placed on top of a larger disc or an empty peg.\n\n" +
                "Click 'OK' to start the game!");

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


