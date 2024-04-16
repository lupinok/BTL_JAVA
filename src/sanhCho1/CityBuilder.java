package sanhCho1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityBuilder {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CityBuilder cityBuilder = new CityBuilder();
            cityBuilder.buildCity();
        });
    }

    private void buildCity() {
        JFrame frame = new JFrame("City Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Kích thước của cửa sổ
        int width = 1422; // Đặt chiều rộng
        int height = 810; // Đặt chiều cao
        frame.setSize(width, height);

        // Tạo một panel chứa cả thành phố và thanh taskbar
        CityPanel cityPanel = new CityPanel("image/City.png");
        cityPanel.setPreferredSize(new Dimension(width, height));

        // Thêm thanh taskbar vào dưới cùng của panel
        JPanel taskbarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        taskbarPanel.setOpaque(false); // Đặt thành phần này là trong suốt
        taskbarPanel.setPreferredSize(new Dimension(width, 150)); // Đặt kích thước cho thanh taskbar

        // Tải hình ảnh cho "Túi đồ" và "Quà online"
        ImageIcon bagIcon = new ImageIcon("image/tuiDo.png");
        ImageIcon giftIcon = new ImageIcon("image/qua1.png");
        
     // Thay thế việc tạo JButton bằng CustomButton
        CustomButton bagButton = new CustomButton(bagIcon);
        CustomButton giftButton = new CustomButton(giftIcon);

        // Đặt nền trong suốt cho các nút
        bagButton.setOpaque(false);
        bagButton.setContentAreaFilled(false); // Đặt khu vực nội dung của nút là trong suốt
        bagButton.setBorderPainted(false); // Loại bỏ viền của nút

        giftButton.setOpaque(false);
        giftButton.setContentAreaFilled(false); // Đặt khu vực nội dung của nút là trong suốt
        giftButton.setBorderPainted(false); // Loại bỏ viền của nút


        taskbarPanel.add(bagButton);
        taskbarPanel.add(giftButton);

        cityPanel.add(taskbarPanel, BorderLayout.SOUTH);

        frame.add(cityPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class CityPanel extends JPanel {
        private BufferedImage cityImage;
        private List<Building> buildings;

        public CityPanel(String imagePath) {
            setLayout(new BorderLayout());
            try {
                cityImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            buildings = createBuildings();

            addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseEntered(MouseEvent e) {
            	    for (Building building : buildings) {
            	        if (building.contains(e.getX(), e.getY())) {
            	            building.setHover(true);
            	            break;
            	        }
            	    }
            	    repaint(); // Gọi repaint() chỉ cho CityPanel
            	}

            	@Override
            	public void mouseExited(MouseEvent e) {
            	    for (Building building : buildings) {
            	        building.setHover(false);
            	    }
            	    repaint(); // Gọi repaint() chỉ cho CityPanel
            	}
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    for (Building building : buildings) {
                        if (building.contains(e.getX(), e.getY())) {
                            building.setHover(true);
                        } else {
                            building.setHover(false);
                        }
                    }
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = (getWidth() - cityImage.getWidth()) / 2;
            int y = (getHeight() - cityImage.getHeight()) / 2;
            g.drawImage(cityImage, x, y, null);
            // Vẽ các tòa nhà
            for (Building building : buildings) {
                building.draw(g, x, y);
            }
        }

        private List<Building> createBuildings() {
            List<Building> buildings = new ArrayList<>();
            buildings.add(new Building("image/tiemRen.png", 450, 370));
            buildings.add(new Building("image/Shop.png", 640, 180));
            buildings.add(new Building("image/vienChinh.png", 950, 190));
            buildings.add(new Building("image/vienChinh.png", 450, 1500));
            // Thêm các tòa nhà khác nếu cần
            return buildings;
        }
    }

    static class Building {
        private BufferedImage image;
        private int x;
        private int y;
        private boolean hover;

        public Building(String imagePath, int x, int y) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.x = x;
            this.y = y;
            this.hover = false;
        }

        public void draw(Graphics g, int offsetX, int offsetY) {
            Graphics2D g2d = (Graphics2D) g;
            // Đặt độ trong suốt dựa trên trạng thái hover
            if (hover) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            } else {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
            // Vẽ hình ảnh của tòa nhà
            g.drawImage(image, x + offsetX, y + offsetY, null);
        }

        public boolean contains(int mouseX, int mouseY) {
            // Kiểm tra xem điểm chuột có nằm trong tòa nhà không
            return mouseX >= x && mouseX <= x + image.getWidth() &&
                    mouseY >= y && mouseY <= y + image.getHeight();
        }

        public void setHover(boolean hover) {
            this.hover = hover;
        }
    }
}
