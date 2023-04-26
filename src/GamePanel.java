import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private int level;
    private int lives;
    private int candiesSpeed;
    private int maxCandiesToCollect;
    private int score;
    private boolean isDead;
    private BackgroundPanel backgroundPanel;
    private Bowl bowl;
    private ArrayList<Candy> candies;
    private ArrayList<Bomb> bombs;

    private GameOverPanel gameOverPanel;

    public GamePanel (int level, int candiesSpeed, int maxCandiesToCollect, BackgroundPanel backgroundPanel) {
        this.level = level;
        this.lives = Constants.MAX_LIVES;
        this.candiesSpeed = candiesSpeed;
        this.maxCandiesToCollect = maxCandiesToCollect;
        this.score = 0;
        this.isDead = false;
        this.candies = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.backgroundPanel = backgroundPanel;

        this.setBounds(Constants.MENU_PANEL_WIDTH, Constants.Y_START, Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH, Constants.GAME_WINDOW_HEIGHT);
        this.setLayout(null);

        livesLabel = new JLabel("*****");
        livesLabel.setBounds(Constants.X_START + Constants.MARGIN_LABEL, Constants.Y_START, Constants.LIVES_LABEL_SIZE*4,Constants.LIVES_LABEL_SIZE);
        Font livesFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LIVES_LABEL_SIZE);
        livesLabel.setFont(livesFont);
        this.add(livesLabel);

        scoreLabel = new JLabel("0/" + this.maxCandiesToCollect);
        scoreLabel.setBounds(this.getWidth()-(Constants.SCORE_LABEL_SIZE*3+Constants.MARGIN_LABEL), Constants.Y_START, Constants.SCORE_LABEL_SIZE*3,Constants.SCORE_LABEL_SIZE);
        Font ScoreFont = new Font("Comic Sans MS", Font.PLAIN, Constants.SCORE_LABEL_SIZE);
        scoreLabel.setFont(ScoreFont);
        this.add(scoreLabel);

        bowl = new Bowl(this);
        bowl.start();

        for (int i = 0; i < this.maxCandiesToCollect*10; i++) {
            Candy candy = new Candy(this.candiesSpeed, this);
            candies.add(candy);
        }

        for (int i = 0; i < this.maxCandiesToCollect*10; i++) {
            Bomb bomb = new Bomb(this.candiesSpeed*2, this);
            bombs.add(bomb);
        }

        this.mainGameLoop();
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        Color color1 = new Color(194, 223, 255);
        Color color2 = new Color(231, 153, 163);
        GradientPaint gradient = new GradientPaint(0, 0, color2, getWidth(), getHeight(), color1);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setPaint(gradient);
        g2d.fillRect(Constants.X_START, Constants.Y_START, Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        bowl.paint(graphics);
        for (Candy candy : candies) {
            candy.paint(graphics);
        }
        for (Bomb bomb : bombs) {
            bomb.paint(graphics);
        }
    }

    private void updateScore () {
        this.score++;
        updateScoreLabel();
//        checkScore();
    }

    private void updateScoreLabel () {
        String result = this.score + "/" + this.maxCandiesToCollect;
        this.scoreLabel.setText(result);
    }

    private void updateLives () {
        this.lives--;
        updateLivesLabel();
        if (this.lives == 0) {
            isDead = true;
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
        gameOverPanel = new GameOverPanel((Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH-Constants.GAME_OVER_PANEL_WIDTH-Constants.MARGIN_BUTTON)/2, ((Constants.GAME_WINDOW_HEIGHT-Constants.GAME_OVER_PANEL_HEIGHT-2*Constants.MARGIN_BUTTON)/2), Constants.GAME_OVER_PANEL_WIDTH, Constants.GAME_OVER_PANEL_HEIGHT, this, backgroundPanel);
        this.add(gameOverPanel);
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

            for (int i = 0; i < candies.size(); i++) {
                Candy candy = candies.get(i);
                int delay = i * 8000; // set a delay of i seconds between each candy drop
                new Thread(() -> {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new Thread(candy).start();
                }).start();
            }

            while (true) {
                if (checkCollisions()) {
                    updateScore();
                    sleep(10);
                }
                if (checkIfMissedCandy()) {
                    updateLives();
                    sleep(10);
                }
            }
        }).start();

        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(new Movement(this.bowl, this));
            for (int i = 0; i < bombs.size(); i++) {
                Bomb bomb = bombs.get(i);
                int delay = i * 8000; // set a delay of i seconds between each candy drop
                new Thread(() -> {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new Thread(bomb).start();
                }).start();
            }
        }).start();
    }

    private void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkCollisions() {
        boolean result = false;
        for (int i = 0; i < candies.size(); i++) {
            Candy candy = candies.get(i);
            if (candy.getX() >= bowl.getX() && candy.getX() <= bowl.getX() + bowl.getWidth() - Constants.CANDY_WIDTH) {
                if (candy.getY() == Constants.GAME_WINDOW_HEIGHT - bowl.getHeight() - candy.getHeight() - 20) {
                    result = true;
                    candies.remove(i);
                    repaint();
                    break;
                }
            }
        }
        return result;
    }

    private boolean checkIfMissedCandy () {
        boolean result = false;
        for (int i = 0; i < candies.size(); i++) {
            Candy candy = candies.get(i);
            if (candy.getY()>this.bowl.getY()) {
                result = true;
                candies.remove(i);
                repaint();
                break;
            }
        }
        return result;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getLevel() {
        return level;
    }

    public int getCandiesSpeed() {
        return candiesSpeed;
    }

    public int getMaxCandiesToCollect() {
        return maxCandiesToCollect;
    }
}
