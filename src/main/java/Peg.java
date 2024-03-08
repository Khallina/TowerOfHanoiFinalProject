import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Stack;

public class Peg {
    private Rectangle bounds;
    private Stack<Disk> disks;

    public Peg(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.disks = new Stack<>();
    }

    public void addDisk(Disk disk) {
        disks.push(disk);
        int newY = bounds.y + bounds.height - (disks.size() * disk.getBounds().height);
        disk.moveTo(bounds.x + (bounds.width / 2) - (disk.getBounds().width / 2), newY);
    }

    public Disk removeTopDisk() {
        return disks.pop();
    }

    public Disk peekTopDisk() {
        return disks.isEmpty() ? null : disks.peek();
    }

    public void draw(Graphics g) {
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        for (Disk disk : disks) {
            disk.draw(g);
        }
    }

    public boolean isEmpty() {
        return disks.isEmpty();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
