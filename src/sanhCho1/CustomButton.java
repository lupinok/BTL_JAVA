package sanhCho1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private boolean hover;

    public CustomButton(Icon icon) {
        super(icon);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        hover = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            // Add pressed state effect if needed
        } else if (hover) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            super.paintComponent(g2d);
            g2d.dispose();
        } else {
            super.paintComponent(g);
        }
    }
}
