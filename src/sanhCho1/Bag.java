package sanhCho1;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Bag extends JPanel {
	// HashMap để lưu trữ số lượng của từng loại item
    private HashMap<String, Integer> itemCounts = new HashMap<>();
    private Image backgroundImage;
    private int padding = 20; // Đặt giá trị của padding
    private int padding_top = 70;

    public Bag() {
        setLayout(new GridLayout(0, 6)); // Sử dụng GridLayout để sắp xếp các item theo cột
        loadImage("image/bgr.png"); // Load hình ảnh nền cho bảng
        
     // Đặt padding cho JPanel Bag
        setBorder(BorderFactory.createEmptyBorder(padding_top, padding, padding, padding));
        
     // Đặt phần nền của JPanel trong suốt
        setOpaque(false);

        // Thêm các item vào shop
        addBagItem(new BagItem("Item 1", "image/trang bị/rương/rương1.png"));
        addBagItem(new BagItem("Item 2", "image/trang bị/rương/rương2.png"));
        addBagItem(new BagItem("Item 3", "image/trang bị/rương/rương3.png"));
        addBagItem(new BagItem("Item 1", "image/trang bị/rương/rương1.png"));
        addBagItem(new BagItem("Item 2", "image/trang bị/rương/rương2.png"));
        addBagItem(new BagItem("Item 3", "image/trang bị/rương/rương3.png"));
        addBagItem(new BagItem("Item 1", "image/trang bị/rương/rương1.png"));
        addBagItem(new BagItem("Item 2", "image/trang bị/rương/rương2.png"));

        
        // Đặt kích thước cho bảng bag
        setPreferredSize(new Dimension(960, 640));
    }

    private void loadImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ hình ảnh nền của bảng Bag
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void addBagItem(BagItem item) {
        String itemName = item.getItemName();
        
        // Loại bỏ tất cả các phiên bản cũ của item trong JPanel
        removeAllItemsWithName(itemName);

        // Thêm item vào bag
        add(item);
        
     // Cập nhật số lượng của item trong Item1
        int itemCount = 1; // Bắt đầu với số lượng là 1
        if (itemCounts.containsKey(itemName)) {
            itemCount = itemCounts.get(itemName) + 1; // Tăng số lượng lên 1 nếu item đã tồn tại trong HashMap
        }
        item.setQuantity(itemCount);

        // Lưu trữ số lượng của item vào HashMap
        itemCounts.put(itemName, itemCount);
     // Đảm bảo rằng kích thước của item không thay đổi
        revalidate();
        repaint();
    }
    
 // Phương thức để loại bỏ tất cả các phiên bản cũ của một item khỏi JPanel
    private void removeAllItemsWithName(String itemName) {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof BagItem) {
                BagItem item = (BagItem) component;
                if (item.getItemName().equals(itemName)) {
                    remove(item);
                }
            }
        }
    }
    
    public HashMap<String, Integer> getItemCounts() {
        return itemCounts;
    }
}
