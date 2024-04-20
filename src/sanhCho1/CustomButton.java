package sanhCho1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private ImageIcon normalIcon;
    private ImageIcon hoverIcon;

    public CustomButton(ImageIcon normalIcon, ImageIcon hoverIcon) {
        this.normalIcon = normalIcon;
        this.hoverIcon = hoverIcon;

        setIcon(normalIcon);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(normalIcon);
            }
        });
    }
}
