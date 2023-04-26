import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Bomb extends Thread {

    private int x;
    private int y;
    private int speed;
    private int width;
    private int height;
    GamePanel gamePanel;

    public Bomb (int speed, GamePanel gamePanel) {
        Random random = new Random();
        int bound = Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH-Constants.BOMB_WIDTH-Constants.MARGIN_BUTTON;
        this.x = random.nextInt(Constants.X_START, bound);
        this.y = -100;
        this.speed = speed;
        this.gamePanel = gamePanel;
    }

    public void run () {
        while (!gamePanel.isDead()) {
            this.y += this.speed;
            sleep(100);
            this.gamePanel.repaint();
        }
    }

    public static void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void paint (Graphics graphics) {
        ImageIcon bombImageIcon = new ImageIcon("C:\\Users\\xmich\\IdeaProjects\\Semester B\\sadnat tichnut\\CandyCollector\\CandyCollector\\src\\Images\\clipart128386 (1).png");
        bombImageIcon.paintIcon(this.gamePanel, graphics, this.x, this.y);
        this.width = Constants.BOMB_WIDTH;
        this.height = Constants.BOMB_HEIGHT;
    }

}
