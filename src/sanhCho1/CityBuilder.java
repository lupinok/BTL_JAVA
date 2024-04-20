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
        
        // Tạo MoneyBar và thêm nó vào phía trên thành phố
        ImageIcon moneyBarBackground = new ImageIcon("image/money.png"); // Thay thế bằng hình ảnh nền thực tế
        MoneyBar moneyBar = new MoneyBar(1000, moneyBarBackground); // Thay 1000 bằng số tiền ban đầu
        cityPanel.add(moneyBar, BorderLayout.NORTH);

        // Thêm thanh taskbar vào dưới cùng của panel
        JPanel taskbarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        taskbarPanel.setOpaque(false); // Đặt thành phần này là trong suốt
        taskbarPanel.setPreferredSize(new Dimension(width, 150)); // Đặt kích thước cho thanh taskbar

     // Tải hình ảnh cho "Túi đồ" và "Quà online"
        ImageIcon bagNormalIcon = new ImageIcon("image/tuiDo.png");
        ImageIcon bagHoverIcon = new ImageIcon("image/tuiDo1.png");
        ImageIcon giftNormalIcon = new ImageIcon("image/qua1.png");
        ImageIcon giftHoverIcon = new ImageIcon("image/qua2.png");

        // Thay thế việc tạo JButton bằng CustomButton
        CustomButton bagButton = new CustomButton(bagNormalIcon, bagHoverIcon);
        CustomButton giftButton = new CustomButton(giftNormalIcon, giftHoverIcon);

        // Đặt nền trong suốt cho các nút
        bagButton.setOpaque(false);
        bagButton.setContentAreaFilled(false); // Đặt khu vực nội dung của nút là trong suốt
        bagButton.setBorderPainted(false); // Loại bỏ viền của nút

        giftButton.setOpaque(false);
        giftButton.setContentAreaFilled(false); // Đặt khu vực nội dung của nút là trong suốt
        giftButton.setBorderPainted(false); // Loại bỏ viền của nút

     // Thêm sự kiện lắng nghe cho nút "tuiDo"
        bagButton.addActionListener(e -> openBag());

        taskbarPanel.add(bagButton);
        taskbarPanel.add(giftButton);

        cityPanel.add(taskbarPanel, BorderLayout.SOUTH);

        frame.add(cityPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

 // Phương thức để mở Bag
    private void openBag() {
        JFrame bagFrame = new JFrame("Bag");
        bagFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng frame Bag mà không ảnh hưởng tới ứng dụng chính
        bagFrame.getContentPane().add(new Bag());
        bagFrame.pack();
        bagFrame.setLocationRelativeTo(null);
        bagFrame.setVisible(true);
    }
    
    static class CityPanel extends JPanel {
    	// Khai báo biến shopWindowVisible
        private boolean shopWindowVisible = false;

     // Phương thức tạo cửa sổ dialog hiển thị mặt hàng của shop
        private void showShopItemsDialog() {
            // Tạo một đối tượng Shop
            Shop shop = new Shop();

            // Tạo cửa sổ JDialog chứa bảng mặt hàng từ lớp Shop
            JDialog shopDialog = new JDialog();
            shopDialog.setTitle("Shop Items");
            shopDialog.getContentPane().add(shop); // Thêm bảng shop vào JDialog
            shopDialog.pack();
            shopDialog.setLocationRelativeTo(null);
            shopDialog.setVisible(true);
        }

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
                public void mouseClicked(MouseEvent e) {
                    for (Building building : buildings) {
                        if ("Shop".equals(building.getBuildingName()) && building.contains(e.getX(), e.getY())) {
                            // Kiểm tra xem người dùng có đang click vào tòa nhà "Shop" không
                            showShopItemsDialog();
                            break;
                        }
                    }
                }

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
            buildings.add(new Building("image/tiemRen.png", 450, 375, "tiemRen"));
            buildings.add(new Building("image/Shop.png", 640, 180, "Shop"));
            buildings.add(new Building("image/vienChinh.png", 950, 190, "vienChinh"));
            buildings.add(new Building("image/vienChinh.png", 450, 1500, "them"));
            // Thêm các tòa nhà khác nếu cần
            return buildings;
        }
        
    }

    static class Building {
    	private BufferedImage image;
        private int x;
        private int y;
        private boolean hover;
        private String name; // Thêm trường name để lưu trữ tên của tòa nhà
        private String buildingName; // Thêm một biến để lưu trữ tên của tòa nhà

        public Building(String imagePath, int x, int y, String name) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.x = x;
            this.y = y;
            this.hover = false;
            this.name = name; // Đặt tên của tòa nhà
            this.buildingName = name;
        }

        public String getBuildingName() {
            return buildingName;
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
