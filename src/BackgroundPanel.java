import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    JButton playButton;
    JButton instructionButton;
    JLabel levelLabel;
    MenuPanel menuPanel;
    GamePanel gamePanel;

    public BackgroundPanel () {
        this.setBounds(Constants.X_START, Constants.Y_START, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);
        this.setLayout(null);
        menuPanel = new MenuPanel(Constants.X_START,Constants.Y_START,Constants.MENU_PANEL_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        playButton = new JButton("PLAY");
        playButton.setBounds((Constants.INSTRUCTION_BUTTON_WIDTH+(2*Constants.MARGIN_BUTTON)-Constants.PLAY_BUTTON_WIDTH)/2,Constants.MARGIN_BUTTON,Constants.PLAY_BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        playButton.setBackground(Color.decode("#E799A3"));
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.BUTTON_BORDERS_THICKNESS));
        Font startFont = new Font("Comic Sans MS", Font.BOLD, Constants.BUTTON_FONT_SIZE);
        playButton.setFont(startFont);
        playButton.setFocusable(false);
        menuPanel.add(playButton);

        instructionButton = new JButton("How to play");
        instructionButton.setBounds(Constants.MARGIN_BUTTON, playButton.getY() + Constants.MARGIN_BUTTON + playButton.getHeight(),Constants.INSTRUCTION_BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        instructionButton.setBackground(Color.decode("#E799A3"));
        instructionButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.BUTTON_BORDERS_THICKNESS));
        Font instructionFont = new Font("Comic Sans MS", Font.BOLD, Constants.BUTTON_FONT_SIZE);
        instructionButton.setFont(instructionFont);
        instructionButton.setFocusable(false);
        menuPanel.add(instructionButton);

        levelLabel = new JLabel("Level: 1");
        int levelLabelWidth = Constants.LEVEL_LABEL_SIZE*4;
        levelLabel.setBounds((Constants.MENU_PANEL_WIDTH-levelLabelWidth)/2, 2*(instructionButton.getY() + Constants.BUTTON_HEIGHT + Constants.MARGIN_BUTTON), levelLabelWidth,Constants.LEVEL_LABEL_SIZE);
        Font levelFont = new Font("Comic Sans MS", Font.BOLD, Constants.LEVEL_LABEL_SIZE);
        levelLabel.setFont(levelFont);
        menuPanel.add(levelLabel);

        this.add(menuPanel);
        this.setVisible(true);

        // play button job
        playButton.addActionListener((e) -> {
            playButton.setVisible(false);
            gamePanel = new GamePanel(Constants.FIRST_LEVEL, Constants.CANDIES_SPEED_FIRST_LEVEL, Constants.MAX_CANDIES_FIRST_LEVEL, this);
            gamePanel.setVisible(true);
            this.add(gamePanel);
            this.repaint();
        });

        // instruction button job
        instructionButton.addActionListener((e) -> {
            new InstructionWindow();
        });

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Color color1 = new Color(194, 223, 255);
        Color color2 = new Color(231, 153, 163);
        GradientPaint gradient = new GradientPaint(0, 0, color2, getWidth(), getHeight(), color1);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setPaint(gradient);
        g2d.fillRect(Constants.X_START, Constants.Y_START, getWidth(), getHeight());
    }

    public void changeToNextLevel (int level) {
        updateLevelLabel(level);
        if (level==2) {
            GamePanel gamePanel = new GamePanel(Constants.SECOND_LEVEL, Constants.CANDIES_SPEED_SECOND_LEVEL, Constants.MAX_CANDIES_SECOND_LEVEL, this);
            gamePanel.setVisible(true);
            this.add(gamePanel);
            gamePanel.requestFocusInWindow();
            this.repaint();
        } else {
            GamePanel gamePanel = new GamePanel(Constants.THIRD_LEVEL, Constants.CANDIES_SPEED_THIRD_LEVEL, Constants.MAX_CANDIES_THIRD_LEVEL, this);
            gamePanel.setVisible(true);
            this.add(gamePanel);
            gamePanel.requestFocusInWindow();
            this.repaint();
        }
    }

    private void updateLevelLabel (int level) {
        if (level==2) {
            levelLabel.setText("Level: 2");
        } else {
            levelLabel.setText("Level: 3");
        }
    }

    public void startAgain (int level, int candiesSpeed, int maxCandies) {
        this.add(new GamePanel(level, candiesSpeed, maxCandies, this));
    }
}
