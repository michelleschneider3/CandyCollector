import javax.swing.*;
import java.awt.*;

public class CandyCollector extends JFrame {
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
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        this.add(backgroundPanel);
        this.setVisible(true);
    }

}