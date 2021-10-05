import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class ScoreBar extends JPanel {

    public static final int PERIOD_1S = 1000;
    public static final int FONT_SIZE = 26;

    private int totalMines;
    private int time;

    private Timer gameTimer;

    private final JLabel mineLabel;
    private final JLabel timerLabel;
    private final JCheckBox highlightModeCheckBox;
    private GameManager gameManager;

    public ScoreBar() {
        this.totalMines = 0;
        this.time = 0;
        this.gameTimer = new Timer();


        this.mineLabel = new JLabel(minesProgress(0, this.totalMines));
        this.mineLabel.setFont(new GameFont(FONT_SIZE));
        JPanel minePanel = new JPanel();
        minePanel.add(mineLabel);

        this.timerLabel = new JLabel(time + "s");
        this.timerLabel.setFont(new GameFont(FONT_SIZE));
        JPanel timerPanel = new JPanel();
        timerPanel.add(timerLabel);

        this.highlightModeCheckBox = new JCheckBox("Highlight Mode");
        this.highlightModeCheckBox.setFont(new GameFont(22));
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(this.highlightModeCheckBox);

        this.setLayout(new GridLayout(1, 2));
        this.add(minePanel);
        this.add(timerPanel);
        this.add(checkBoxPanel);

        this.gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time++;
                updateTimerText();
            }
        }, 0, PERIOD_1S);
    }

    public static String minesProgress(int found, int total) {
        return found + " / " + total + " Mines";
    }

    public void setTotalMines(int totalMines) {
        this.totalMines = totalMines;
        this.setMinesFound(0);
    }

    public void setMinesFound(int foundMines) {
        this.mineLabel.setText(minesProgress(foundMines, this.totalMines));
    }

    public void updateTimerText() {
        this.timerLabel.setText(this.time + "s");
    }

    public void setManager(GameManager gameManager) {
        this.gameManager = gameManager;

        this.highlightModeCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gameManager.toggleHighlightMode();
            }
        });
    }

    public void startTimer() {
        this.gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time++;
                updateTimerText();
            }
        }, 0, PERIOD_1S);
    }

    public void stopTimer() {
        this.gameTimer.cancel();
    }
}
