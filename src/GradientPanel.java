import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private int x;
    private int y;
    private int width;
    private int height;

    String firstColor;

    String secondColor;

    public GradientPanel(int x, int y, int width, int height, String firstColor, String secondColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.setDoubleBuffered(true);
        this.setBounds(x,y,width,height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Define the gradient
        GradientPaint gradient = new GradientPaint(this.x, this.y, Color.decode(this.firstColor), this.width, this.height, Color.decode(this.secondColor));

        // Set the paint to the gradient and fill the panel
        g2d.setPaint(gradient);
        g2d.fillRect(this.x, this.y, this.width, this.height);
    }
}
