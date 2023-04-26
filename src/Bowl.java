import javax.swing.*;
import java.awt.*;

public class Bowl extends Thread{

    private int x;
    private int y;
    private int width;
    private int height;
    private GamePanel gamePanel;
    private ImageIcon bowlImageIcon;

    public Bowl (GamePanel gamePanel) {
        bowlImageIcon = new ImageIcon("C:\\Users\\xmich\\IdeaProjects\\Semester B\\sadnat tichnut\\CandyCollector\\CandyCollector\\src\\Images\\kindpng_2083291 (6).png");
        height = bowlImageIcon.getIconHeight();
        width = bowlImageIcon.getIconWidth();
        this.x = (Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH-width)/2;
        this.y = Constants.GAME_WINDOW_HEIGHT-2*height;
        this.gamePanel = gamePanel;
    }

    public void paint (Graphics graphics) {
        ImageIcon bowlImageIcon = new ImageIcon("C:\\Users\\xmich\\IdeaProjects\\Semester B\\sadnat tichnut\\CandyCollector\\CandyCollector\\src\\Images\\kindpng_2083291 (6).png");
        bowlImageIcon.paintIcon(this.gamePanel, graphics, this.x, this.y);
        this.width = bowlImageIcon.getIconWidth();

    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }
}
