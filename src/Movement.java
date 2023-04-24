import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Movement implements KeyListener {

    Bowl bowl;
    GamePanel gamePanel;

    public Movement (Bowl bowl, GamePanel gamePanel) {
        this.bowl = bowl;
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int width = bowl.getWidth();
        if (e.getKeyCode() == KeyEvent.VK_LEFT  && bowl.getX() > Constants.X_START+1) {
            int x = bowl.getX() - 2;
            bowl.setX(x);
            bowl.setY(bowl.getY());
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT  && bowl.getX() < (Constants.GAME_WINDOW_WIDTH-Constants.MENU_PANEL_WIDTH)-(width+20)) {
            int x = bowl.getX() + 2;
            bowl.setX(x);
            bowl.setY(bowl.getY());
        }
        gamePanel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
