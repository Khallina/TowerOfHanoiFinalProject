import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Disk {
    private int number;
    private Color color;
    private Rectangle bounds;

    public Disk(int number, Color color, int x, int y, int width, int height) {
        this.number = number;
        this.color = color;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(number), bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    public int getSize() {
        return number;
    }

    public void moveTo(int x, int y) {
        bounds.setLocation(x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
