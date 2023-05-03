import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class Candy extends Thread {
    private int x;
    private int y;
    private int speed;
    private int width;
    private int height;
    private GamePanel gamePanel;

    public Candy(int speed, GamePanel gamePanel) {
        Random random = new Random();
        int bound = Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH-Constants.CANDY_WIDTH-Constants.MARGIN_BUTTON;
        this.x = random.nextInt(Constants.X_START,bound);
        this.y = Constants.START_FALLING;
        this.speed = speed;
        this.gamePanel = gamePanel;
    }

    public void run () {
        while (!gamePanel.isDead()) {
            this.y += this.speed;
            sleep(Constants.SLEEP_RUN);
            this.gamePanel.repaint();
        }
    }

    private void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void paint (Graphics graphics) {
        ImageIcon candyImageIcon = new ImageIcon("src/Images/candy.png");
        candyImageIcon.paintIcon(this.gamePanel, graphics, this.x, this.y);
        this.width = candyImageIcon.getIconWidth();
        this.height = candyImageIcon.getIconHeight();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }
}
