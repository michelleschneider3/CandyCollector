import javax.swing.*;
import java.awt.*;
import java.util.Random;
public abstract class FallingObject extends Thread {
    protected int x;
    protected int y;
    protected int speed;
    protected int width;
    protected int height;
    protected GamePanel gamePanel;
    protected ImageIcon imageIcon;

    public FallingObject(ImageIcon imageIcon, int speed, GamePanel gamePanel) {
        Random random = new Random();
        int bound = Constants.GAME_WINDOW_WIDTH - Constants.MENU_PANEL_WIDTH - imageIcon.getIconWidth() - Constants.MARGIN_BUTTON;
        this.x = random.nextInt(Constants.X_START, bound);
        this.y = Constants.START_FALLING;
        this.speed = speed;
        this.gamePanel = gamePanel;
        this.imageIcon = imageIcon;
    }

    public void run() {
        while (!gamePanel.isDead()) {
            this.y += this.speed;
            sleep(Constants.SLEEP_RUN);
            this.gamePanel.repaint();
        }
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void paint(Graphics graphics) {
        imageIcon.paintIcon(this.gamePanel, graphics, this.x, this.y);
        this.width = imageIcon.getIconWidth();
        this.height = imageIcon.getIconHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }
}