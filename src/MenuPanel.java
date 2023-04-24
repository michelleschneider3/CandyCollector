import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private int x;
    private int y;
    private int width;
    private int height;

    public MenuPanel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        Color color1 = new Color(194, 223, 255);
        this.setBackground(color1);
    }
}
