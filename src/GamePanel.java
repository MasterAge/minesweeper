import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class GamePanel extends JPanel {

    private ScoreBar scoreBar;
    private GameManager manager;
    private GameFrame gameFrame;

    public GamePanel(GameFrame gameFrame, int gameWidth, int gameHeight, int mines) {
        this.gameFrame = gameFrame;
        this.scoreBar = new ScoreBar();
        this.manager = new GameManager(this, scoreBar, gameWidth, gameHeight, mines);

        GridLayout gridLayout = new GridLayout(manager.getGameWidth(), manager.getGameHeight());
        JPanel panel = new JPanel(gridLayout);

        Tile[][] tiles = new Tile[manager.getGameWidth()][manager.getGameHeight()];
        for (int i = 0; i < manager.getGameWidth(); i++) {
            for (int j = 0; j < manager.getGameHeight(); j++) {
                tiles[i][j] = new Tile(i, j, manager);
                panel.add(tiles[i][j]);
            }
        }
        manager.setButtons(tiles);

        this.setLayout(new BorderLayout());
        this.add(scoreBar, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }


    public void endGame() {
        this.gameFrame.endGame();
    }
}
