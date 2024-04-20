
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DoHoaTTK extends JFrame implements KeyListener {
    private Image backGroundTTK;
    private Image DiaHinhTTK;
    private Image TenMapTTK;
    private Image DaoTrenKhong; //Ảnh đảo trên không
    private Image character1ImageTTK; // Ảnh của nhân vật số 1
    private Image character2ImageTTK; // Ảnh của nhân vật số 2
    private int character1X = 100; // Vị trí x của nhân vật số 1
    private int character1Y = 595; // Vị trí y của nhân vật số 1
    private int character2X = 1240; // Vị trí x của nhân vật số 2
    private int character2Y = 576; // Vị trí y của nhân vật số 2
    private final int CHARACTER_SPEED = 6; // Tốc độ di chuyển của nhân vật số 1
    private boolean isFacingLeft = false; // Theo dõi hướng di chuyển của nhân vật số 1
    
    // Thanh máu của nhân vật số 1
    private int healthBarWidth = 90; // Độ rộng của thanh máu
    private int healthBarHeight = 10; // Độ cao của thanh máu
    private int healthBarX = character1X +30; // Vị trí x của thanh máu
    private int healthBarY = character1Y - healthBarHeight + 10; // Vị trí y của thanh máu
    //Thanh máu của nhân vật số 2
    private int character2Health = 100; // Sức khỏe ban đầu của nhân vật số 2
    private int character2MaxHealth = 100; // Sức khỏe tối đa của nhân vật số 2
    private int character2HealthBarWidth = 90; // Độ rộng của thanh máu của nhân vật số 2
    private int character2HealthBarHeight = 10; // Độ cao của thanh máu của nhân vật số 2
    private int character2HealthBarX = character2X ; // Vị trí x của thanh máu của nhân vật số 2
    private int character2HealthBarY = character2Y - character2HealthBarHeight - 4 ; // Vị trí y của thanh máu của nhân vật số 2
    
    
    public DoHoaTTK() {
        setTitle("Gunny Game");
        setSize(1422, 810);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        try {
            backGroundTTK = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\GiaoDienTTK\\src\\ImagesTTK\\backGroundTTK.jpg").getImage();
            DiaHinhTTK = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\GiaoDienTTK\\src\\ImagesTTK\\DiaHinhTTK.png").getImage();
            DaoTrenKhong = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\GiaoDienTTK\\src\\ImagesTTK\\DaoTrenKhong.png").getImage();
            TenMapTTK = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\GiaoDienTTK\\src\\ImagesTTK\\TenMapTTK.png").getImage();
            character1ImageTTK = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\GiaoDienTTK\\src\\ImagesTTK\\BoyTTK.png").getImage(); // Load ảnh của nhân vật số 1
            character2ImageTTK = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\GiaoDienTTK\\src\\ImagesTTK\\BossTTK.png").getImage(); // Load ảnh của nhân vật số 2
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading images: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        JButton backBT = new JButton("<< Back");
        backBT.setBounds(20, 20, 100, 40);
        backBT.setBackground(Color.yellow);
        backBT.setForeground(Color.red);
        add(backBT);
            
       //Tạo nút power
        JButton powerBT = new JButton("Bộc phá");
        powerBT.setBounds(40, 180, 100, 40);
        powerBT.setBackground(Color.red);
        powerBT.setForeground(Color.yellow);
        add(powerBT);
        JButton flyBT = new JButton("Bay");
        flyBT.setBounds(40, 240, 100, 40);
        flyBT.setBackground(Color.green);
        flyBT.setForeground(Color.red);
        add(flyBT);
        JButton x2BT = new JButton("x2 Đạn");
        x2BT.setBounds(40, 300, 100, 40);
        x2BT.setBackground(Color.yellow);
        x2BT.setForeground(Color.red);
        add(x2BT);
        // Thêm KeyListener vào JFrame
        addKeyListener(this);
        setFocusable(true);

        // Tạo một lớp JPanel mới để vẽ hình ảnh và thanh máu
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                doDrawing(g);
            }
        };

        // Thêm panel vào JFrame
        add(panel);
        // Hiển thị cửa sổ
        setVisible(true);   
    }
    
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        float alphaBG = 0.5f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaBG));
        // Vẽ ảnh nền
        g2d.drawImage(backGroundTTK, 0, 0, getWidth(), getHeight(), this);

        // Thiết lập độ trong suốt của ảnh Địa Hình
        float alpha = 1f; // Độ trong suốt từ 0.0 (hoàn toàn trong suốt) đến 1.0 (hoàn toàn không trong suốt)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(DaoTrenKhong, 222, 100, 1000, 500, this);
        // Vẽ ảnh Địa Hình
        float alpha1 = 1f; // Độ trong suốt từ 0.0 (hoàn toàn trong suốt) đến 1.0 (hoàn toàn không trong suốt)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha1));
        g2d.drawImage(DiaHinhTTK, 0, 205, 1450, 900, this);
       
        float alpha2 = 1f; // Độ trong suốt của ảnh Tên Map
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2));
        g2d.drawImage(TenMapTTK, 500, -50, 450, 200, null);

        // Vẽ nhân vật số 1
        if (isFacingLeft) {
            // Nếu nhân vật đang hướng trái, phản chiếu hình ảnh
            g2d.drawImage(character1ImageTTK, character1X + 150, character1Y, -150, 150, this);
        } else {
            // Nếu nhân vật đang hướng phải, vẽ bình thường
            g2d.drawImage(character1ImageTTK, character1X, character1Y, 150, 150, this);
        }

        // Vẽ thanh máu của nhân vật số 1
        g2d.setColor(Color.GRAY); // Đặt màu nền là màu xám
        g2d.fillRect(healthBarX - 1, healthBarY - 1, healthBarWidth + 2, healthBarHeight + 2); // Vẽ nền màu xám
        g2d.setColor(Color.RED); // Đặt màu fill là màu đỏ cho thanh máu
        g2d.fillRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
        g2d.setColor(Color.YELLOW); // Đặt màu viền là màu vàng
        g2d.drawRect(healthBarX - 2, healthBarY - 2, healthBarWidth + 2, healthBarHeight + 2); // Vẽ viền màu vàng xung quanh thanh máu
        // Vẽ thanh máu của nhân vật số 2
        g2d.setColor(Color.GRAY); // Đặt màu nền là màu xám
        g2d.fillRect(character2HealthBarX - 1, character2HealthBarY - 1, character2HealthBarWidth + 2, character2HealthBarHeight + 2); // Vẽ nền màu xám
        g2d.setColor(Color.RED); // Đặt màu fill là màu đỏ cho thanh máu của nhân vật số 2
        g2d.fillRect(character2HealthBarX, character2HealthBarY, (int) ((double) character2Health / character2MaxHealth * character2HealthBarWidth), character2HealthBarHeight);
        g2d.setColor(Color.YELLOW); // Đặt màu viền là màu vàng
        g2d.drawRect(character2HealthBarX - 2, character2HealthBarY - 2, character2HealthBarWidth + 2, character2HealthBarHeight + 2); // Vẽ viền màu vàng xung quanh thanh máu của nhân vật số 2


        // Vẽ nhân vật số 2
        g2d.drawImage(character2ImageTTK, character2X , character2Y, 140, 140, this);

        // Giải phóng Graphics2D
        g2d.dispose();
    }

    // Xử lý sự kiện từ bàn phím
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Xử lý phím di chuyển cho nhân vật số 1
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                character1X -= CHARACTER_SPEED; // Di chuyển sang trái
                isFacingLeft = true; // Đặt hướng di chuyển sang trái
                break;
            case KeyEvent.VK_RIGHT:
                character1X += CHARACTER_SPEED; // Di chuyển sang phải
                isFacingLeft = false; // Đặt hướng di chuyển sang phải
                break;
        }
        // Cập nhật vị trí của thanh máu
        healthBarX = character1X + 20;
        healthBarY = character1Y - healthBarHeight + 10;

        repaint(); // Vẽ lại màn hình sau khi di chuyển
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Không cần xử lý sự kiện này
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Không cần xử lý sự kiện này
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DoHoaTTK(); 
        });
    }
}