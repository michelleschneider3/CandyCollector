import javax.swing.*;
import java.awt.*;

public class InstructionWindow extends JFrame {

    public InstructionWindow () {
        this.setSize(Constants.INSTRUCTION_WINDOW_WIDTH,Constants.INSTRUCTION_WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("How To Play");
        this.setLayout(null);
        MenuPanel instructionPanel = new MenuPanel(Constants.X_START, Constants.Y_START, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.INSTRUCTION_WINDOW_HEIGHT);
        this.add(instructionPanel);

        JLabel firstLineLabel = new JLabel("Your goal is to collect candies in order to gain a score to move to the next level.");
        firstLineLabel.setBounds(Constants.MARGIN_LABEL,Constants.Y_START,Constants.INSTRUCTION_WINDOW_WIDTH,Constants.LABEL_HEIGHT);
        Font firstLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
        firstLineLabel.setFont(firstLineFont);
        instructionPanel.add(firstLineLabel);

        JLabel secondLineLabel = new JLabel("You have to avoid the bombs that can cause the end of the game.");
        secondLineLabel.setBounds(Constants.MARGIN_LABEL, firstLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT );
        Font secondLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
        secondLineLabel.setFont(secondLineFont);
        instructionPanel.add(secondLineLabel);

        JLabel thirdLineLabel = new JLabel("You have 5 lives, each missed candy takes 1 life from you.");
        thirdLineLabel.setBounds(Constants.MARGIN_LABEL, secondLineLabel.getY() + secondLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT);
        Font thirdLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
        thirdLineLabel.setFont(thirdLineFont);
        instructionPanel.add(thirdLineLabel);

        JLabel fourthLineLabel = new JLabel("To move right - press the right key,");
        fourthLineLabel.setBounds(Constants.MARGIN_LABEL, thirdLineLabel.getY() + thirdLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT);
        Font fourthLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
        fourthLineLabel.setFont(fourthLineFont);
        instructionPanel.add(fourthLineLabel);

        JLabel fifthLineLabel = new JLabel("To move left - press the left key.");
        fifthLineLabel.setBounds(Constants.MARGIN_LABEL, fourthLineLabel.getY() + fourthLineLabel.getHeight() + Constants.MARGIN_LABEL, Constants.INSTRUCTION_WINDOW_WIDTH, Constants.LABEL_HEIGHT);
        Font fifthLineFont = new Font("Comic Sans MS", Font.PLAIN, Constants.LABEL_SIZE);
        fifthLineLabel.setFont(fifthLineFont);
        instructionPanel.add(fifthLineLabel);

        ImageIcon KeysImageIcon = new ImageIcon("src/Images/kindpng_3551319 (1).png");
        JLabel KeysImageLabel = new JLabel(KeysImageIcon);
        KeysImageLabel.setSize(KeysImageIcon.getIconWidth(), KeysImageIcon.getIconHeight());
        KeysImageLabel.setBounds((Constants.INSTRUCTION_WINDOW_WIDTH-KeysImageIcon.getIconWidth())/2,fifthLineLabel.getY() + fifthLineLabel.getHeight() + 3*Constants.MARGIN_LABEL,KeysImageIcon.getIconWidth(),KeysImageIcon.getIconHeight());
        instructionPanel.add(KeysImageLabel);

        this.setVisible(true);
    }
}
