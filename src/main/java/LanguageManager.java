import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private ResourceBundle messages;
    private Locale currentLocale;

    public LanguageManager(Locale locale, ResourceBundle messages) {
        this.currentLocale = locale;
        this.messages = messages;
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        this.messages = ResourceBundle.getBundle("messages", currentLocale);
    }

    public String getMessage(String key) {
        return this.messages.getString(key);
    }
}
