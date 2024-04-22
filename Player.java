package animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;

public class Player extends JPanel {
    private BufferedImage moveSpriteSheet;
    private BufferedImage standSpriteSheet;
    private int moveCurrentFrame = 0;
    private int standCurrentFrame = 0;
    private int moveFrameWidth, standFrameWidth, frameHeight;
    private int numMoveFrames = 2; // Số lượng khung hình trong sprite sheet di chuyển
    private int numStandFrames = 3; // Số lượng khung hình trong sprite sheet đứng
    private int frameDelay = 100; // Độ trễ giữa các khung hình (milliseconds)
    private long lastFrameTime;

    private boolean isMoving = false; // Mặc định nhân vật đứng yên

    public Player() {
        // Load sprite sheets
        ImageIcon moveIcon = new ImageIcon("C:\\Users\\Admin\\Desktop\\JAVA\\BTL\\TÀI NGUYÊN\\character\\boò.png");
        moveSpriteSheet = new BufferedImage(moveIcon.getIconWidth(), moveIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        moveSpriteSheet.getGraphics().drawImage(moveIcon.getImage(), 0, 0, null);

        ImageIcon standIcon = new ImageIcon("C:\\Users\\Admin\\Desktop\\JAVA\\BTL\\TÀI NGUYÊN\\character\\stand.png");
        standSpriteSheet = new BufferedImage(standIcon.getIconWidth(), standIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        standSpriteSheet.getGraphics().drawImage(standIcon.getImage(), 0, 0, null);

        // Set frame width and height for move animation
        moveFrameWidth = moveSpriteSheet.getWidth() / numMoveFrames;
        // Set frame width and height for stand animation
        standFrameWidth = standSpriteSheet.getWidth() / numStandFrames;
        frameHeight = moveSpriteSheet.getHeight(); // Assuming frame height is same for both animations

        // Set preferred size of the panel based on frame size
        setPreferredSize(new Dimension(moveFrameWidth, frameHeight));

        // Start animation timer
        lastFrameTime = System.currentTimeMillis();
        Timer timer = new Timer(16, e -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameTime >= frameDelay) {
                if (isMoving) {
                    moveCurrentFrame = (moveCurrentFrame + 1) % numMoveFrames;
                } else {
                    standCurrentFrame = (standCurrentFrame + 1) % numStandFrames;
                }
                repaint();
                lastFrameTime = currentTime;
            }
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_A) {
                    startMovingLeft();
                } else if (keyCode == KeyEvent.VK_D) {
                    startMovingRight();
                } else if (keyCode == KeyEvent.VK_W) {
                    startStanding();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
                    stopMoving();
                }
            }
        });
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isMoving) {
            // Draw current frame for moving animation
            g.drawImage(moveSpriteSheet.getSubimage(moveCurrentFrame * moveFrameWidth, 0, moveFrameWidth, frameHeight), 0, 0, null);
        } else {
            // Draw current frame for standing animation
            g.drawImage(standSpriteSheet.getSubimage(standCurrentFrame * standFrameWidth, 0, standFrameWidth, frameHeight), 0, 0, null);
        }
    }

    public void startMovingLeft() {
        isMoving = true;
        // Gọi phương thức để xử lý di chuyển sang trái
    }

    public void startMovingRight() {
        isMoving = true;
        // Gọi phương thức để xử lý di chuyển sang phải
    }

    public void stopMoving() {
        isMoving = false;
        // Gọi phương thức để xử lý dừng di chuyển
    }

    public void startStanding() {
        isMoving = false;
        // Gọi phương thức để xử lý đứng yên
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Character Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Player());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
