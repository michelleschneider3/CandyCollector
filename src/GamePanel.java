import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    JLabel scoreLabel;
    JLabel livesLabel;
    private int level;
    private int lives;
    private int candiesSpeed;
    private int maxCandiesToCollect;
    private int score;
    private Candy candy;
    BackgroundPanel backgroundPanel;
    Bowl bowl;

    public GamePanel (int level, int candiesSpeed, int maxCandiesToCollect) {
        this.level = level;
        this.lives = Constants.MAX_LIVES;
        this.candiesSpeed = candiesSpeed;
        this.maxCandiesToCollect = maxCandiesToCollect;
        this.score = 0;

        this.setBounds(Constants.MENU_PANEL_WIDTH, Constants.Y_START, Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH, Constants.GAME_WINDOW_HEIGHT);
        this.setOpaque(false);
        this.setLayout(null);

        livesLabel = new JLabel("*****");
        livesLabel.setBounds(Constants.X_START + Constants.MARGIN_LABEL, Constants.Y_START, Constants.LIVES_LABEL_SIZE*4,Constants.LIVES_LABEL_SIZE);
        Font livesFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LIVES_LABEL_SIZE);
        livesLabel.setFont(livesFont);
        this.add(livesLabel);

        scoreLabel = new JLabel("0");
        scoreLabel.setBounds(this.getWidth()-(Constants.SCORE_LABEL_SIZE*2), Constants.Y_START, Constants.SCORE_LABEL_SIZE*2,Constants.SCORE_LABEL_SIZE);
        Font ScoreFont = new Font("Comic Sans MS", Font.PLAIN, Constants.SCORE_LABEL_SIZE);
        scoreLabel.setFont(ScoreFont);
        this.add(scoreLabel);

        bowl = new Bowl();
        bowl.start();
        candy = new Candy(2);
        candy.start();
        this.mainGameLoop();
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        candy.paint(graphics);
        bowl.paint(graphics);
    }

    private void updateScore () {
        this.score++;
        checkScore();
    }

    private void updateScoreLabel () {


    }

    private void updateLives () {
        this.lives--;
        updateLivesLabel();
        if (this.lives == 0) {
            gameOver();
        }
    }

    private void updateLivesLabel () {
        String result = "";
        for (int i = 0; i < this.lives; i++) {
            result += "*";
        }
        this.livesLabel.setText(result);
    }

    private void gameOver() {

    }

    private void checkScore () {
        if (this.score==maxCandiesToCollect) {
               backgroundPanel.changeToNextLevel(this.level);
        }
    }

    private void mainGameLoop () {
        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(new Movement(this.bowl, this));


        }).start();
    }

    private static void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
