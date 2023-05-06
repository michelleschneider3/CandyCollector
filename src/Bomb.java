import javax.swing.*;
public class Bomb extends FallingObject {
    public Bomb(int speed, GamePanel gamePanel) {
        super(new ImageIcon("src/Images/bomb.png"), speed, gamePanel);
    }
}