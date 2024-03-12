import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static ResourceBundle messages;
    private static Locale currentLocale;

    public static void initialize() {
        currentLocale = Locale.getDefault();
        messages = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        messages = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static String getMessage(String key) {
        return messages.getString(key);
    }
}
