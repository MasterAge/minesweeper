import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameManager {
    public static final int MINE = 10;
    public static final int EMPTY = 0;
    public static final int FLAG = -1;

    public static final Map<Integer, Color> HINT_COLOURS = Map.of(
            1, new Color(15, 105, 225),
            2, new Color(7, 157, 24),
            3, new Color(241, 57, 57),
            4, new Color(137, 8, 199),
            5, new Color(187, 146, 7),
            6, new Color(11, 220, 171),
            7, new Color(0, 0, 0),
            8, new Color(236, 7, 217)
    );

    private final int gameWidth;
    private final int gameHeight;
    private final int totalTiles;
    private final int maxMines;

    int[][] tiles;
    private int flags;
    private boolean highlightMode;
    private boolean firstClick;

    private final GamePanel gameWindow;
    private final ScoreBar scoreBar;
    private Tile[][] gameButtons;

    public GameManager(GamePanel gamePanel, ScoreBar scoreBar, int gameWidth, int gameHeight, int maxMines) {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.totalTiles = gameHeight * gameWidth;
        this.maxMines = maxMines;
        this.flags = 0;
        this.tiles = new int[gameWidth][gameHeight];
        this.highlightMode = false;
        this.firstClick = false;

        this.gameWindow = gamePanel;
        this.scoreBar = scoreBar;
        this.scoreBar.setTotalMines(this.maxMines);
        this.scoreBar.setManager(this);

        new Random().ints(this.maxMines, 0, totalTiles)
                    .forEach(mine -> tiles[mine / gameWidth][mine % gameHeight] = MINE);

        for (int i = 0; i < gameWidth; i++) {
            for (int j = 0; j < gameHeight; j++) {
                int adjacentMines = 0;

                if (tiles[i][j] == MINE) {
                    continue;
                }

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int x = i + k;
                        int y = j + l;

                        if (x < 0 || y < 0 || x >= gameWidth | y >= gameHeight) {
                            continue;
                        }

                        if (tiles[x][y] == MINE) {
                            adjacentMines++;
                        }

                    }
                }

                tiles[i][j] = adjacentMines;
            }
        }

    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public int getTotalTiles() {
        return totalTiles;
    }

    public int getMaxMines() {
        return maxMines;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setButtons(Tile[][] buttons) {
        this.gameButtons = buttons;
    }

    public void toggleHighlightMode() {
        this.highlightMode = !this.highlightMode;
    }

    public void revealTile(int row, int column, boolean flag) {
        int tile = tiles[row][column];
        Tile button = gameButtons[row][column];
        if (button.isFlipped()) {
            return;
        }

        if (!firstClick) {
            firstClick = true;
//            scoreBar.startTimer();
        }

        if (flag) {
            if (button.isFlagged()) {
                button.unflag();
                scoreBar.setMinesFound(--this.flags);
            } else if (this.flags < this.maxMines) {
                button.flag();
                scoreBar.setMinesFound(++this.flags);
            }
        } else if (tile == MINE) {
            this.scoreBar.stopTimer();
            showMines();
            JOptionPane.showMessageDialog(gameWindow, "You loose.");
            gameWindow.endGame();
            return;
        } else {
            if (tile > 0) {
                button.setNumber(Integer.toString(tile), GameManager.HINT_COLOURS.get(tile));
            } else {
                showCells(row, column);
            }
        }

        if (checkWin()) {
            this.scoreBar.stopTimer();
            JOptionPane.showMessageDialog(gameWindow, "You Win!");
            gameWindow.endGame();
        }
    }

    private void showCells(int row, int column) {
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                int x = row + k;
                int y = column + l;

                if (outsideGrid(x, y) || gameButtons[x][y].isFlipped()) {
                    continue;
                }

                int tile = tiles[x][y];
                Tile button = gameButtons[x][y];
                if (tile != MINE) {

                    if (tile > 0) {
                        button.setNumber(Integer.toString(tile), GameManager.HINT_COLOURS.get(tile));
                    } else if (!(x == row && y == column)) {
                        button.flip();
                        showCells(x, y);

                    } else {
                        button.flip();
                    }
                }
            }
        }
    }

    public void showMines() {
        for (int i = 0; i < gameWidth; i++) {
            for (int j = 0; j < gameHeight; j++) {
                if (tiles[i][j] == MINE) {
                    gameButtons[i][j].showMine();
                }
            }
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < gameWidth; i++) {
            for (int j = 0; j < gameHeight; j++) {
                Tile button = gameButtons[i][j];
                if (!button.isFlipped() && (tiles[i][j] != MINE || !button.isFlagged())) {
                    return false;
                }
            }
        }

        return true;
    }

    public void highlightSurrounding(int row, int column) {
        this.setSurroundingBorder(row, column, Color.BLUE);
    }

    public void removeSurroundingHighlight(int row, int column) {
        this.setSurroundingBorder(row, column, null);
    }

    private void setSurroundingBorder(int row, int column, Color color) {
        if (!highlightMode) {
            return;
        }

        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                int x = row + k;
                int y = column + l;

                if (outsideGrid(x, y)) {
                    continue;
                }

                gameButtons[x][y].enableHighlight(color);
            }
        }
    }

    private boolean outsideGrid(int x, int y)
    {
        return x < 0 || y < 0 || x >= gameWidth || y >= gameHeight;
    }
}
