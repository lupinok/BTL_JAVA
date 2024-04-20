package sanhCho1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Tạo lớp Item để hiển thị một mục và giá tiền
class ShopItem extends JPanel {
	
	private boolean hover = false;
	private String itemName;
    private int itemPrice;
    private Image labelImage; // Ảnh money
	
	public void setHover(boolean hover) {
	    this.hover = hover;
	    repaint();
	}
	
    public ShopItem(String itemName, String imagePath, int itemPrice) {
    	
    	this.itemName = itemName;
        this.itemPrice = itemPrice;
        
        // Load hình ảnh money
        loadLabelImage("image/money.png");
        
    	setLayout(new BorderLayout());
        setOpaque(false); // Đặt JPanel này là trong suốt
        
        // Thêm hình ảnh cho mặt hàng
        ImageIcon itemIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(itemIcon);
        add(imageLabel, BorderLayout.CENTER);
        
     

        // Tạo panel chứa cả nhãn thông tin và hình ảnh thông tin
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false); // Đặt JPanel này là trong suốt
        add(infoPanel, BorderLayout.SOUTH);

        
        // Thêm lắng nghe sự kiện chuột
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setHover(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setHover(false);
            }
        });
    }
    
    public int getItemPrice() {
        return itemPrice;
    }
    
    private void loadLabelImage(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        labelImage = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Gọi super.paintComponent() trước
        // Load hình ảnh nền và thay đổi kích thước
        ImageIcon bgIcon;
        if (hover) {
            bgIcon = new ImageIcon("image/bgritem2.png"); // Nếu hover, sử dụng bgritem2
        } else {
            bgIcon = new ImageIcon("image/bgritem1.png"); // Ngược lại, sử dụng bgritem1
        }

        Image bgImage = bgIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        bgIcon = new ImageIcon(bgImage);
        g.drawImage(bgIcon.getImage(), 0, 0, this);
        
        // Vẽ nhãn thông tin sau khi vẽ nền ảnh, nhưng trước khi vẽ hình ảnh thông tin
        if (labelImage != null) {
            int labelWidth = getWidth() -20; // Chiều ngang của hình ảnh được đặt là 1/4 của chiều ngang của Item
            int labelHeight =labelImage.getHeight(this);// Tính toán chiều cao tương ứng
            g.drawImage(labelImage, getWidth() / 2 - labelWidth / 2, getHeight() - 50, labelWidth, labelHeight, this);
        }
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE); // Thiết lập màu cho chữ
        g2d.setFont(new Font("Arial", Font.BOLD, 14)); // Thiết lập font cho chữ
        FontMetrics fm = g2d.getFontMetrics(); // Lấy đối tượng FontMetrics để lấy kích thước của chữ
        int textWidth = fm.stringWidth(itemName + ": " + itemPrice + "$"); // Lấy chiều rộng của chữ
        int x = (getWidth() - textWidth) / 2; // Tính toạ độ x để chữ được căn giữa theo chiều ngang
        int y = getHeight() - 28; // Tính toạ độ y để chữ được căn giữa theo chiều dọc
        g2d.drawString(itemName + ": " + itemPrice + "$", x, y); // Vẽ chữ
        g2d.dispose(); // Giải phóng Graphics2D

    }
}
