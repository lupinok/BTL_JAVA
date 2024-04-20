package sanhCho1;
import javax.swing.*;
import java.awt.*;

public class MoneyBar extends JPanel {
    private int moneyAmount;
    private ImageIcon backgroundImage;

    public MoneyBar(int moneyAmount, ImageIcon backgroundImage) {
        this.moneyAmount = moneyAmount;
        this.backgroundImage = backgroundImage;
        setOpaque(false);
        setPreferredSize(new Dimension(backgroundImage.getIconWidth(), backgroundImage.getIconHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ hình ảnh nền
        g.drawImage(backgroundImage.getImage(), 0, 0, this);
        // Vẽ số tiền
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Font và kích thước có thể thay đổi
        g.drawString("Tiền: " + moneyAmount + "$", 70, 25); // Vị trí và định dạng có thể thay đổi
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
        repaint();
    }
}
