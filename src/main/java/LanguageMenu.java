import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class LanguageMenu extends JDialog {
    private JComboBox<String> languageComboBox;

    public LanguageMenu(JFrame parent) {
        super(parent, "Language Menu", true); // Set the title and make it modal
        setSize(300, 300);
        setPreferredSize(new Dimension(300, 300));
        setLayout(null);

        JLabel selectLabel = new JLabel("Select Language:");
        selectLabel.setBounds(20, 20, 120, 25);
        add(selectLabel);

        String[] languages = {"English", "Japanese", "Spanish", "French", "Korean"};
        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setBounds(150, 20, 120, 25);
        add(languageComboBox);

        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(100, 70, 100, 30);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyLanguage();
            }
        });
        add(applyButton);
    }

    private void applyLanguage() {
        int selectedLanguageIndex = languageComboBox.getSelectedIndex();
        switch (selectedLanguageIndex) {
            case 0: // English
                LanguageManager.setLocale(Locale.ENGLISH);
                break;
            case 1: // Japanese
                LanguageManager.setLocale(Locale.JAPANESE);
                break;
            case 2: // Spanish
                LanguageManager.setLocale(new Locale("es", "ES"));
                break;
            case 3: // French
                LanguageManager.setLocale(Locale.FRENCH);
                break;
            case 4: // Korean
                LanguageManager.setLocale(Locale.KOREAN);
                break;
            default:
                break;
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 150);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                new LanguageMenu(frame).setVisible(true);
            }
        });
    }
}
