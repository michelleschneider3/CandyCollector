import javax.swing.*;
import java.awt.*;
public class GameCompletionPanel extends JPanel{
    private int x;
    private int y;
    private int width;
    private int height;
    private JLabel gameCompletionLabel;
    public GameCompletionPanel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setBounds(x,y,width,height);
        this.setLayout(null);
        this.setOpaque(false);

        gameCompletionLabel = new JLabel("GREAT JOB");
        gameCompletionLabel.setBounds(Constants.X_START,Constants.Y_START,Constants.GAME_OVER_PANEL_WIDTH,Constants.GAME_OVER_PANEL_HEIGHT/4);
        Font gameCompletionLabelFont = new Font("Comic Sans MS", Font.BOLD, Constants.GAME_OVER_PANEL_HEIGHT/4);
        gameCompletionLabel.setFont(gameCompletionLabelFont);
        this.add(gameCompletionLabel);
    }
}
