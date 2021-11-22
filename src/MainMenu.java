import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu extends JPanel {

    public MainMenu(GameFrame gameFrame) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Minesweeper");
        heading.setFont(new GameFont(56));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton easyGame = makeMenuButton("Easy", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 10% of spaces are mines
                gameFrame.newGame(10, 10, 10);
            }
        });

        JButton mediumGame = makeMenuButton("Medium", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 25% of spaces are mines
                gameFrame.newGame(15, 12, 20);
            }
        });

        JButton hardGame = makeMenuButton("Hard", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // A lot of mines
                gameFrame.newGame(20, 20, 50);
            }
        });

        this.add(Box.createRigidArea(new Dimension(0, 40)));
        this.add(heading);
        this.add(Box.createRigidArea(new Dimension(0, 40)));
        this.add(easyGame);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(mediumGame);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(hardGame);
    }

    public static JButton makeMenuButton(String text, MouseListener mouseListener)
    {
        JButton button = new JButton(text);
        button.setFont(new GameFont(26));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addMouseListener(mouseListener);
        return button;
    }
}
