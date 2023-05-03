import javax.swing.*;
import java.awt.*;
public class GameOverPanel extends JPanel {
    private int x;
    private int y;
    private int width;
    private int height;
    private JLabel gameOverLabel;
    private JButton startAgainButton;
    private GamePanel gamePanel;
    private BackgroundPanel backgroundPanel;
    public GameOverPanel (int x, int y, int width, int height, GamePanel gamePanel, BackgroundPanel backgroundPanel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gamePanel = gamePanel;
        this.setBounds(x,y,width,height);
        this.setLayout(null);
        this.setOpaque(false);
        this.backgroundPanel = backgroundPanel;

        gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setBounds(Constants.X_START,Constants.Y_START,Constants.GAME_OVER_PANEL_WIDTH,Constants.GAME_OVER_PANEL_HEIGHT/4);
        Font gameOverLabelFont = new Font("Comic Sans MS", Font.BOLD, Constants.GAME_OVER_PANEL_HEIGHT/4);
        gameOverLabel.setFont(gameOverLabelFont);
        this.add(gameOverLabel);

        startAgainButton = new JButton("Start Again");
        startAgainButton.setBounds((Constants.GAME_OVER_PANEL_WIDTH-Constants.INSTRUCTION_BUTTON_WIDTH)/2,gameOverLabel.getY()+gameOverLabel.getHeight()+Constants.MARGIN_BUTTON,Constants.INSTRUCTION_BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        startAgainButton.setBackground(Color.decode("#E799A3"));
        startAgainButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.BUTTON_BORDERS_THICKNESS));
        Font startAgainFont = new Font("Comic Sans MS", Font.BOLD, Constants.BUTTON_FONT_SIZE);
        startAgainButton.setFont(startAgainFont);
        startAgainButton.setFocusable(false);
        this.add(startAgainButton);

        startAgainButton.addActionListener((e) -> {
            this.setVisible(false);
            this.gamePanel.setVisible(false);
            backgroundPanel.startAgain(gamePanel.getLevel());
        });
    }
}
