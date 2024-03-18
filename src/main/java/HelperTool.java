import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelperTool extends JFrame {
    public HelperTool(LanguageManager languageManager) {
        setTitle(languageManager.getMessage("helper.stuck"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(languageManager.getMessage("helper.prompt"));

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