import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Candy extends Thread {
    private int x;
    private int y;
    private int speed;
    private int width;

    private int height;

    GamePanel gamePanel;

    public Candy (int speed) {
        Random random = new Random();
        int bound = Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH-Constants.CANDY_WIDTH;
        this.x = random.nextInt(Constants.X_START,bound);
        this.y = this.width;
        this.speed = speed;
    }

    public void run () {
        while (true) {
            this.y += this.speed;
            sleep(100);
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
        ImageIcon candyImageIcon = new ImageIcon("C:\\Users\\xmich\\IdeaProjects\\Semester B\\sadnat tichnut\\CandyCollector\\CandyCollector\\src\\Images\\4461 (2).png");
        candyImageIcon.paintIcon(this.gamePanel, graphics, this.x, this.y);
        this.width = candyImageIcon.getIconWidth();
        this.height = candyImageIcon.getIconHeight();
    }

    public int getY() {
        return y;
    }
}
