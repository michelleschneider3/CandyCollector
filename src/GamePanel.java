import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class GamePanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private final int level;
    private int lives;
    private final int candiesSpeed;
    private final int maxCandiesToCollect;
    private int score;
    private final int delay;
    private boolean isDead;
    private BackgroundPanel backgroundPanel;
    private final Bowl bowl;
    private ArrayList<Candy> candies;
    private ArrayList<Bomb> bombs;
    private GameOverPanel gameOverPanel;
    private GameCompletionPanel gameCompletionPanel;
    private Clip candyCollectClip;
    private Clip gameOverClip;

    public GamePanel (int level, int candiesSpeed, int maxCandiesToCollect, BackgroundPanel backgroundPanel, int delay) {
        this.level = level;
        this.lives = Constants.MAX_LIVES;
        this.candiesSpeed = candiesSpeed;
        this.maxCandiesToCollect = maxCandiesToCollect;
        this.score = 0;
        this.isDead = false;
        this.candies = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.backgroundPanel = backgroundPanel;
        this.delay = delay;

        this.setBounds(Constants.MENU_PANEL_WIDTH, Constants.Y_START, Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH, Constants.GAME_WINDOW_HEIGHT);
        this.setLayout(null);
        this.setOpaque(false);

        livesLabel = new JLabel("*****");
        livesLabel.setBounds(Constants.X_START + Constants.MARGIN_LABEL, Constants.Y_START, Constants.LIVES_LABEL_SIZE*4,Constants.LIVES_LABEL_SIZE);
        Font livesFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LIVES_LABEL_SIZE);
        livesLabel.setFont(livesFont);
        this.add(livesLabel);

        scoreLabel = new JLabel("0/" + this.maxCandiesToCollect);
        scoreLabel.setBounds(this.getWidth()-Constants.SCORE_LABEL_WIDTH, Constants.Y_START, Constants.SCORE_LABEL_SIZE*3,Constants.SCORE_LABEL_SIZE);
        Font ScoreFont = new Font("Comic Sans MS", Font.PLAIN, Constants.SCORE_LABEL_SIZE);
        scoreLabel.setFont(ScoreFont);
        this.add(scoreLabel);

        loadSound();

        bowl = new Bowl(this);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(new Movement(this.bowl, this));

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
        bowl.paint(graphics);
        for (Candy candy : candies) {
            candy.paint(graphics);
        }
        for (Bomb bomb : bombs) {
            bomb.paint(graphics);
        }
    }

    private void loadSound () {
        try {
            candyCollectClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/Sounds/Y2Mate.is - BLOOP SOUND EFFECT-hdsKW9pUeQY-128k-1656609360400 (1).wav"));
            candyCollectClip.open(inputStream);
            FloatControl gainControl = (FloatControl) candyCollectClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15f);

            gameOverClip = AudioSystem.getClip();
            AudioInputStream inputStream2 = AudioSystem.getAudioInputStream(new File("src/Sounds/Funny GAME OVER _ Free Sound Effect.wav"));
            gameOverClip.open(inputStream2);
            FloatControl gainControl2 = (FloatControl) gameOverClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl2.setValue(-15f);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void updateScore () {
        if (this.score<this.maxCandiesToCollect) {
            this.score++;
            if (this.score<this.maxCandiesToCollect) {
                updateScoreLabel();
            } else {
                if (this.level != Constants.LEVELS[4]) {
                    updateScoreLabel();
                    this.isDead = true;
                    changeToNextLevel();
                } else {
                    updateScoreLabel();
                    this.backgroundPanel.writeToFile(this.backgroundPanel.getLevelFile(), "Completed");
                    this.isDead = true;
                    gameCompletion();
                }
            }
        }
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
        gameOverClip.setFramePosition(0);
        gameOverClip.start();
        gameOverPanel = new GameOverPanel((Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH-Constants.GAME_OVER_PANEL_WIDTH-Constants.MARGIN_BUTTON)/2, ((Constants.GAME_WINDOW_HEIGHT-Constants.GAME_OVER_PANEL_HEIGHT-2*Constants.MARGIN_BUTTON)/2), Constants.GAME_OVER_PANEL_WIDTH, Constants.GAME_OVER_PANEL_HEIGHT, this, backgroundPanel);
        this.add(gameOverPanel);
    }

    private void gameCompletion() {
        gameCompletionPanel = new GameCompletionPanel((Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH-Constants.GAME_OVER_PANEL_WIDTH-Constants.MARGIN_BUTTON)/2, (Constants.GAME_WINDOW_HEIGHT-Constants.GAME_OVER_PANEL_HEIGHT-2*Constants.MARGIN_BUTTON)/2, Constants.GAME_OVER_PANEL_WIDTH, Constants.GAME_OVER_PANEL_HEIGHT);
        this.add(gameCompletionPanel);
    }

    private void changeToNextLevel() {
        backgroundPanel.changeToNextLevel(this.level+1, this);
    }

    private void mainGameLoop () {
        new Thread(() -> {
            while (!this.isDead) {
                this.setFocusable(true);
                this.requestFocus();
                this.addKeyListener(new Movement(this.bowl, this));
                runObjects(this.candies, this.delay);
                runObjects(this.bombs, this.delay/2);

                while (!this.isDead) {
                    checkCandyCollisions();
                    checkIfMissedCandy();
                    checkBombCollisions();
                }
            }
        }).start();
    }

    private void runObjects (ArrayList<? extends FallingObject> fallingObjects, int currentDelay) {
        for (int i = 0; i < fallingObjects.size(); i++) {
            FallingObject fallingObject = fallingObjects.get(i);
            int delay = i * currentDelay;
            new Thread(() -> {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(fallingObject).start();
            }).start();
        }
    }

    private void checkCandyCollisions() {
        for (int i = 0; i < candies.size(); i++) {
            Candy candy = candies.get(i);
            if (candy.getX() >= bowl.getX()-(Constants.CANDY_WIDTH/2) && candy.getX() <= bowl.getX() + bowl.getWidth() - Constants.CANDY_WIDTH/2 ) {
                if (candy.getY() == Constants.GAME_WINDOW_HEIGHT - bowl.getHeight() - candy.getHeight() - 20) {
                    candyCollectClip.setFramePosition(0);
                    candyCollectClip.start();
                    updateScore();
                    candies.remove(i);
                    break;
                }
            }
        }
    }

    private void checkIfMissedCandy () {
        for (int i = 0; i < candies.size(); i++) {
            Candy candy = candies.get(i);
            if (candy.getY()>this.bowl.getY()) {
                updateLives();
                candies.remove(i);
                break;
            }
        }
    }

    private void checkBombCollisions() {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);

            Rectangle rect1 = new Rectangle(this.bowl.getWidth(), this.bowl.getHeight());
            rect1.setLocation(this.bowl.getX(), this.bowl.getY());

            Rectangle rect2 = new Rectangle(Constants.BOMB_WIDTH, Constants.BOMB_HEIGHT);
            rect2.setLocation(bomb.getX(), bomb.getY());

            if (rect1.intersects(rect2)) {
                this.isDead = true;
                gameOver();
            } else {
                if (bomb.getY()>Constants.GAME_WINDOW_HEIGHT) {
                    bombs.remove(i);
                    break;
                }
            }
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public int getLevel() {
        return level;
    }
}