import java.awt.*;

public class Animation {
    private Disk disk;
    private Peg sourcePeg;
    private Peg targetPeg;
    private int frames;
    private int currentFrame;

    public Animation(Disk disk, Peg sourcePeg, Peg targetPeg, int frames) {
        this.disk = disk;
        this.sourcePeg = sourcePeg;
        this.targetPeg = targetPeg;
        this.frames = frames;
        this.currentFrame = 0;
    }

    public void update() {
        currentFrame++;
        Rectangle sourceBounds = sourcePeg.getBounds();
        Rectangle targetBounds = targetPeg.getBounds();
        int startX = sourceBounds.x + (sourceBounds.width / 2) - (disk.getBounds().width / 2);
        int startY = sourceBounds.y + sourceBounds.height - ((sourcePeg.getDiskCount() + 1) * disk.getBounds().height);
        int targetX = targetBounds.x + (targetBounds.width / 2) - (disk.getBounds().width / 2);
        int targetY = targetBounds.y + targetBounds.height - ((targetPeg.getDiskCount() + 1) * disk.getBounds().height);

        int deltaX = (targetX - startX) / frames;
        int deltaY = (targetY - startY) / frames;

        disk.moveTo(startX + (deltaX * currentFrame), startY + (deltaY * currentFrame));
    }

    public void draw(Graphics g) {
        disk.draw(g);
    }

    public boolean isFinished() {
        return currentFrame >= frames;
    }
}
