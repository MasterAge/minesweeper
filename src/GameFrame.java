import javax.swing.JFrame;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

        KeyboardFocusManager
                .getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new MinesweeperKeyDispatcher(this));
    }

    public void newGame(int gameWidth, int gameHeight, int mines) {
        gamePanel = new GamePanel(this, gameWidth, gameHeight, mines);
        this.remove(mainMenu);
        this.add(gamePanel);
        this.setSize(900,900);
        this.setLocationRelativeTo(null);
    }

    public void endGame() {
        if (gamePanel != null) {
            gamePanel.setVisible(false);
            this.remove(gamePanel);
            this.add(mainMenu);
            this.setFrameSize(400, 500);

            gamePanel = null;
        }
    }

    public void setFrameSize(int width, int height) {
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
    }


}
