import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class MinesweeperKeyDispatcher implements KeyEventDispatcher {

    private final GameFrame gameFrame;

    public MinesweeperKeyDispatcher(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameFrame.endGame();
        }
        return false;
    }
}
