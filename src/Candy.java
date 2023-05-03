import javax.swing.*;

public class Candy extends FallingObject {
    public Candy(int speed, GamePanel gamePanel) {
        super(new ImageIcon("src/Images/candy.png"), speed, gamePanel);
    }
}