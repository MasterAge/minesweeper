import javax.swing.JFrame;
import java.awt.HeadlessException;

public class GameFrame extends JFrame {

    private final MainMenu mainMenu;
    private GamePanel gamePanel;

    public GameFrame() throws HeadlessException {
        super("Minesweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFrameSize(400, 500);

        mainMenu = new MainMenu(this);
        this.add(mainMenu);
        this.setVisible(true);
    }

    public void newGame(int gameWidth, int gameHeight, int mines) {
        gamePanel = new GamePanel(this, gameWidth, gameHeight, mines);
        this.remove(mainMenu);
        this.add(gamePanel);
        this.setSize(900,900);
        this.setLocationRelativeTo(null);
    }

    public void endGame() {
        gamePanel.setVisible(false);
        this.remove(gamePanel);
        this.add(mainMenu);
        this.setFrameSize(400, 500);

        gamePanel = null;
    }

    public void setFrameSize(int width, int height) {
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
    }
}
