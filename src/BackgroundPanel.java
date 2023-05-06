import javax.swing.*;
import java.awt.*;
import java.io.*;
public class BackgroundPanel extends JPanel {
    private JButton playButton;
    private JButton instructionButton;
    private JLabel levelLabel;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private File levelFile;
    private int[] delays;
    private int[] levels;
    private int[] speeds;
    private int[] maxCandies;
    private String[] levelLabels;

    public BackgroundPanel () {
        this.delays = Constants.DELAY_FOR_EACH_LEVEL;
        this.levels = Constants.LEVELS;
        this.speeds = Constants.CANDIES_SPEED_FOR_EACH_LEVEL;
        this.maxCandies = Constants.MAX_CANDIES_FOR_EACH_LEVEL;
        this.levelLabels = Constants.LEVELS_LABEL;
        this.levelFile = creatFile();

        this.setBounds(Constants.X_START, Constants.Y_START, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);
        this.setLayout(null);
        this.menuPanel = new MenuPanel(Constants.X_START,Constants.Y_START,Constants.MENU_PANEL_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        this.playButton = new JButton("PLAY");
        this.playButton.setBounds((Constants.INSTRUCTION_BUTTON_WIDTH+(2*Constants.MARGIN_BUTTON)-Constants.PLAY_BUTTON_WIDTH)/2,Constants.MARGIN_BUTTON,Constants.PLAY_BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        this.playButton.setBackground(Color.decode("#E799A3"));
        this.playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.BUTTON_BORDERS_THICKNESS));
        Font startFont = new Font("Comic Sans MS", Font.BOLD, Constants.BUTTON_FONT_SIZE);
        this.playButton.setFont(startFont);
        this.playButton.setFocusable(false);
        this.menuPanel.add(playButton);

        instructionButton = new JButton("How to play");
        instructionButton.setBounds(Constants.MARGIN_BUTTON, playButton.getY() + Constants.MARGIN_BUTTON + playButton.getHeight(),Constants.INSTRUCTION_BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        instructionButton.setBackground(Color.decode("#E799A3"));
        instructionButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.BUTTON_BORDERS_THICKNESS));
        Font instructionFont = new Font("Comic Sans MS", Font.BOLD, Constants.BUTTON_FONT_SIZE);
        instructionButton.setFont(instructionFont);
        instructionButton.setFocusable(false);
        menuPanel.add(instructionButton);

        levelLabel = new JLabel("Level: ");
        int levelLabelWidth = Constants.LEVEL_LABEL_SIZE*4;
        levelLabel.setBounds((Constants.MENU_PANEL_WIDTH-levelLabelWidth)/2, 2*(instructionButton.getY() + Constants.BUTTON_HEIGHT + Constants.MARGIN_BUTTON), levelLabelWidth,Constants.LEVEL_LABEL_SIZE);
        Font levelFont = new Font("Comic Sans MS", Font.BOLD, Constants.LEVEL_LABEL_SIZE);
        levelLabel.setFont(levelFont);
        menuPanel.add(levelLabel);

        this.add(menuPanel);
        this.setVisible(true);

        this.playButton.addActionListener((e) -> {
            String level = readFromFile(this.levelFile);
            playButton.setVisible(false);
            if (level=="" || level==null) {
                updateLevelLabel(1);
                startAgain(1);
            } else {
                if (level.equals("Completed")) {
                    updateLevelLabel(1);
                    startAgain(1);
                } else {
                    updateLevelLabel(Integer.parseInt(level));
                    startAgain(Integer.parseInt(level));
                }
            }
            this.repaint();
        });

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

        ImageIcon candiesImageIcon = new ImageIcon("src/Images/sweet strawberry drop.png");
        candiesImageIcon.paintIcon(this, g, Constants.MENU_PANEL_WIDTH, 0);
    }

    public void changeToNextLevel (int level, GamePanel gamePanel) {
        gamePanel.setVisible(false);
        updateLevelLabel(level);
        writeToFile(this.levelFile, ""+level);

        for (int i = 0; i < this.levels.length; i++) {
            if (level == i+1) {
                this.gamePanel = new GamePanel(this.levels[i], this.speeds[i], this.maxCandies[i], this, this.delays[i]);
                break;
            }
        }
        this.gamePanel.setVisible(true);
        this.add(this.gamePanel);
        this.gamePanel.requestFocusInWindow();
        this.repaint();
    }

    private void updateLevelLabel (int level) {
        for (int i = 0; i < this.levelLabels.length; i++) {
            if (level == i+1) {
                this.levelLabel.setText(levelLabels[i]);
                break;
            }
        }
    }

    public void startAgain (int level) {
        for (int i = 0; i < this.levelLabels.length; i++) {
            if (level == i+1) {
                this.gamePanel = new GamePanel(this.levels[i], this.speeds[i], this.maxCandies[i], this, this.delays[i]);
                break;
            }
        }
        this.add(this.gamePanel);
    }

    private File creatFile () {
        File file = new File("src/Files/level.txt");
        try {
            boolean success = file.createNewFile();
            if (success) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file");
            e.printStackTrace();
        }
        return file;
    }

    private String readFromFile (File file) {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        String level = "";
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            level = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return level;
    }

    public void writeToFile (File file, String level) {
        try {
            if (file != null && file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(level);
                bufferedWriter.close();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getLevelFile() {
        return levelFile;
    }
}
