import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tile extends JButton {
    public static final Color DEFAULT_COLOR = new Color(194, 193, 193);
    public static final int MAX_SPECTRUM = 255;

    private boolean flipped;
    private boolean flagged;

    private final Border defaultBorder;
    public Image flagImg;
    public Image mineImg;

    public Tile(int row, int column, GameManager manager) {
        super();
        this.flipped = false;
        this.defaultBorder = this.getBorder();

        this.setBackground(DEFAULT_COLOR);
        this.setFont(new GameFont(24));
        this.setMargin(new Insets(1, 1, 2, 1));
        this.setFocusPainted(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean rightClick = e.getButton() == MouseEvent.BUTTON3;
                manager.revealTile(row, column, rightClick);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                manager.highlightSurrounding(row, column);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                manager.removeSurroundingHighlight(row, column);
            }
        });

        try {
            flagImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("Art/Flag.png")));
            mineImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("Art/Mine.png")));
        } catch (IOException e) {
            System.out.println("Could not load flag image.");
        }
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void flip() {
        this.flipped = true;
        this.setBackground(Color.white);
        this.setIcon(null);
    }

    public void flag() {
        flagged = true;
        Image flagImgScaled = this.scaleImage(flagImg, false, DEFAULT_COLOR);
        this.setIcon(new ImageIcon(flagImgScaled));
    }

    public void unflag() {
        flagged = false;
        this.setIcon(null);
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setNumber(String text, Color color) {
        this.flip();
        this.setText(text);
        this.setForeground(color);
    }

    public void enableHighlight(Color color) {
        if (color == null) {
            this.setBorder(defaultBorder);
            if (!this.isFlagged()) {
                this.setBackground((isFlipped()) ? Color.white : DEFAULT_COLOR);
            }
        }
        else
        {
            this.setBorder(BorderFactory.createLineBorder(color));

            if (!this.isFlagged()) {
                this.setBackground(new Color(lightenColor(color.getRed()),
                        lightenColor(color.getGreen()),
                        lightenColor(color.getBlue())));
            }
        }
    }

    public int lightenColor(int spectrum) {
        if (spectrum >= MAX_SPECTRUM - 2) {
            return MAX_SPECTRUM;
        }
        else
        {
            int distanceToWhite = MAX_SPECTRUM - spectrum;
            return (int)(spectrum + ((float)distanceToWhite / 1.2));
        }
    }

    public void showMine() {
        Image mineImgScaled = this.scaleImage(mineImg, true, Color.white);
        this.setIcon(new ImageIcon(mineImgScaled));
        this.setBackground(Color.white);
    }

    private Image scaleImage(Image flagImg, boolean symmetrical, Color background) {
        int newWidth = this.getWidth();
        int newHeight = this.getHeight();

        if (symmetrical) {
            int size = Math.min(newWidth, newHeight);
            newWidth = size;
            newHeight = size;
        }

        Image resultingImage = flagImg.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, background, null);
        return outputImage;
    }
}
