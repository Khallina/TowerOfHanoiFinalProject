import java.awt.Color;

public class ColorThemeManager {
    private Color[][] colorThemes = {
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

    private int currentThemeIndex = 3; // Default theme index

    public void setCurrentThemeIndex(int index) {
        currentThemeIndex = index;
    }

    public Color[] getCurrentColorTheme() {
        return colorThemes[currentThemeIndex];
    }
}
