package sanhCho1;
import javax.swing.*;
import java.awt.*;

public class Shop extends JPanel {
    private Image backgroundImage;
    private int padding = 20; // Đặt giá trị của padding
    private int padding_top = 70;

    public Shop() {
        setLayout(new GridLayout(0, 5)); // Sử dụng GridLayout để sắp xếp các item theo cột
        loadImage("image/bgr.png"); // Load hình ảnh nền cho bảng
        
     // Đặt padding cho JPanel Shop
        setBorder(BorderFactory.createEmptyBorder(padding_top, padding, padding, padding));
        
     // Đặt phần nền của JPanel trong suốt
        setOpaque(false);

        // Thêm các item vào shop
        addShopItem(new ShopItem("RƯƠNG GỖ", "image/trang bị/rương/rương1.png", 10));
        addShopItem(new ShopItem("RƯƠNG BẠC", "image/trang bị/rương/rương2.png", 20));
        addShopItem(new ShopItem("RƯƠNG VÀNG", "image/trang bị/rương/rương3.png", 30));
        addShopItem(new ShopItem("THẮT LƯNG", "image/trang bị/thắt lưng/belt1.png", 10));
        addShopItem(new ShopItem("VÒNG LỤC BẢO", "image/trang bị/vòng cổ/reg1.png", 10));
        addShopItem(new ShopItem("VÒNG HỔ PHÁCH", "image/trang bị/vòng cổ/reg2.png", 20));
        addShopItem(new ShopItem("VÒNG RUBY", "image/trang bị/vòng cổ/reg3.png", 30));
        addShopItem(new ShopItem("LIỀM 1", "image/weapon/liềm/7011.png", 10));
        addShopItem(new ShopItem("LIỀM 2", "image/weapon/liềm/7111.png", 20));
        addShopItem(new ShopItem("LIỀM 3", "image/weapon/liềm/7211.png", 30));
        addShopItem(new ShopItem("LIỀM 4", "image/weapon/liềm/7311.png", 40));
        addShopItem(new ShopItem("LỰU ĐẠN 1", "image/weapon/lựu đạn/02_1_weapon.png", 20));
        addShopItem(new ShopItem("LỰU ĐẠN 2", "image/weapon/lựu đạn/101.png", 40));
        addShopItem(new ShopItem("TIVI", "image/weapon/tivi/321.png", 20));
        addShopItem(new ShopItem("TỦ LẠNH", "image/weapon/tivi/331.png", 20));

        // Đặt kích thước cho bảng shop
        setPreferredSize(new Dimension(960, 640));
    }

    private void loadImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ hình ảnh nền của bảng Shop
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void addShopItem(ShopItem item) {
        // Thêm một Item vào bảng shop
        add(item);
    }
}
