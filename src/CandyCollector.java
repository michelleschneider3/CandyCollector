import javax.swing.*;
import java.awt.*;

public class CandyCollector extends JFrame {
    private JLabel scoreLabel, livesLabel;
    private JButton playButton, instructionButton;
    private JFrame instructionWindow;
    private int score;
    private int lives;
    private JPanel gamePanel;
    private Timer timer;
//    private Candy candy;
//    private Plate plate;


    public static void main(String[] args) {
        new CandyCollector();
    }

    public CandyCollector () {
        this.setSize(Constants.GAME_WINDOW_WIDTH,Constants.GAME_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("CandyCollector");
        this.setLayout(null);
        this.setVisible(true);

        playButton = new JButton("PLAY");
        playButton.setBounds((Constants.INSTRUCTION_BUTTON_WIDTH+(2*Constants.MARGIN_BUTTON)-Constants.PLAY_BUTTON_WIDTH)/2,Constants.MARGIN_BUTTON,Constants.PLAY_BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        playButton.setBackground(Color.decode("#E799A3"));
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.BUTTON_BORDERS_THICKNESS));
        Font startFont = new Font("Comic Sans MS", Font.BOLD, Constants.BUTTON_FONT_SIZE);
        playButton.setFont(startFont);
        this.add(playButton);

        instructionButton = new JButton("How to play");
        instructionButton.setBounds(Constants.MARGIN_BUTTON, playButton.getY() + Constants.MARGIN_BUTTON + playButton.getHeight(),Constants.INSTRUCTION_BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        instructionButton.setBackground(Color.decode("#E799A3"));
        instructionButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.BUTTON_BORDERS_THICKNESS));
        Font instructionFont = new Font("Comic Sans MS", Font.BOLD, Constants.BUTTON_FONT_SIZE);
        instructionButton.setFont(instructionFont);
        this.add( instructionButton);

        instructionButton.addActionListener((event) -> {
            instructionWindow = new JFrame("How to play");
            instructionWindow.setSize(Constants.INSTRUCTION_WINDOW_WIDTH,Constants.INSTRUCTION_WINDOW_HEIGHT);
            instructionWindow.setLocationRelativeTo(null);
            instructionWindow.setResizable(false);
            instructionWindow.setLayout(null);
            instructionWindow.setVisible(true);

            JLabel firstLineLabel = new JLabel("Your mission is to collect as many candies as possible into the basket.");
            firstLineLabel.setBounds(Constants.MARGIN_LABEL,Constants.Y_START,Constants.INSTRUCTION_WINDOW_WIDTH,Constants.LABEL_HEIGHT);
            Font firstLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
            firstLineLabel.setFont(firstLineFont);
            instructionWindow.add(firstLineLabel);

            JLabel secondLineLabel = new JLabel("For each candy you get one point.");
            secondLineLabel.setBounds(Constants.MARGIN_LABEL, firstLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT );
            Font secondLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
            secondLineLabel.setFont(secondLineFont);
            instructionWindow.add(secondLineLabel);

            JLabel thirdLineLabel = new JLabel("You have 5 lives, each missed candy takes 1 life from you.");
            thirdLineLabel.setBounds(Constants.MARGIN_LABEL, secondLineLabel.getY() + secondLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT);
            Font thirdLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
            thirdLineLabel.setFont(thirdLineFont);
            instructionWindow.add(thirdLineLabel);

            JLabel fourthLineLabel = new JLabel("To move right - press the right key,");
            fourthLineLabel.setBounds(Constants.MARGIN_LABEL, thirdLineLabel.getY() + thirdLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT);
            Font fourthLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
            fourthLineLabel.setFont(fourthLineFont);
            instructionWindow.add(fourthLineLabel);

            JLabel fifthLineLabel = new JLabel("To move left - press the left key.");
            fifthLineLabel.setBounds(Constants.MARGIN_LABEL, fourthLineLabel.getY() + fourthLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT);
            Font fifthLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
            fifthLineLabel.setFont(fifthLineFont);
            instructionWindow.add(fifthLineLabel);

            ImageIcon imageIcon = new ImageIcon("src/Images/kindpng_3551319 (1).png");
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
            imageLabel.setBounds((Constants.INSTRUCTION_WINDOW_WIDTH-imageIcon.getIconWidth())/2,fifthLineLabel.getY() + fifthLineLabel.getHeight() + 3*Constants.MARGIN_LABEL,imageIcon.getIconWidth(),imageIcon.getIconHeight());
            instructionWindow.getContentPane().add(imageLabel);

            instructionWindow.getContentPane().add(new GradientPanel(Constants.X_START,Constants.Y_START,Constants.INSTRUCTION_WINDOW_WIDTH, Constants.INSTRUCTION_WINDOW_HEIGHT, "#C2DFFF", "#C2DFFF"));
        });

        this.getContentPane().add(new GradientPanel(Constants.X_START,Constants.Y_START,Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT, "#C2DFFF", "#E799A3"));




    }
}