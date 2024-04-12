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

    	// Các dòng code khác ở đây...


        BufferedImage cityImage = loadCityImage("image/City.png");
        List<Building> buildings = createBuildings();

        JPanel cityPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = (getWidth() - cityImage.getWidth()) / 2;
                int y = (getHeight() - cityImage.getHeight()) / 2;
                g.drawImage(cityImage, x, y, null);
                // Vẽ các tòa nhà
                for (Building building : buildings) {
                    building.draw(g);
                }
            }
        };

        // Đặt layout cho frame là BorderLayout
        frame.setLayout(new BorderLayout());
        // Thêm cityPanel vào vị trí CENTER của BorderLayout
        frame.add(cityPanel, BorderLayout.CENTER);

        cityPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                for (Building building : buildings) {
                    if (building.contains(e.getX(), e.getY())) {
                        building.setHover(true);
                        break;
                    }
                }
                cityPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                for (Building building : buildings) {
                    building.setHover(false);
                }
                cityPanel.repaint();
            }
        });

        cityPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                for (Building building : buildings) {
                    if (building.contains(e.getX(), e.getY())) {
                        building.setHover(true);
                    } else {
                        building.setHover(false);
                    }
                }
                cityPanel.repaint();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private BufferedImage loadCityImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Building> createBuildings() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building("image/tiemRen.png", 375, 400));
        buildings.add(new Building("image/Shop.png", 570, 220));
        buildings.add(new Building("image/vienChinh.png", 875, 210));
        // Thêm các tòa nhà khác nếu cần
        return buildings;
    }

    static class Building {
        private BufferedImage image;
        private int x;
        private int y;
        private boolean hover;

        public Building(String imagePath, int x, int y) {
            this.image = loadImage(imagePath);
            this.x = x;
            this.y = y;
            this.hover = false;
        }

        private BufferedImage loadImage(String imagePath) {
            try {
                return ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            // Đặt độ trong suốt dựa trên trạng thái hover
            if (hover) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            } else {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
            // Vẽ hình ảnh của tòa nhà
            g.drawImage(image, x, y, null);
        }

        public boolean contains(int mouseX, int mouseY) {
            // Kiểm tra xem điểm chuột có nằm trong tòa nhà không
            return mouseX >= x && mouseX <= x + image.getWidth() &&
                    mouseY >= y && mouseY <= y + image.getHeight();
        }

        public void setHover(boolean hover) {
            this.hover = hover;
        }

        public boolean isHover() {
            return hover;
        }
    }
}
