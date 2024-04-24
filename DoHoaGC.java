package GiaoDien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class DoHoaGC extends JFrame implements KeyListener {
    private Image backGroundGC;
    private Image DiaHinhGC;
    private Image TenMapGC;
    private Image DaoTrenKhongGC; // Ảnh đảo trên không
    private Image playerImageGC; // Ảnh của player
    private Image playerDeadImageGC; // Ảnh chết của player
    private Image bossImageGC;
    private Image vongngam;
    
    Player player = new Player(100, 630); // Khởi tạo đối tượng player
    Boss boss = new Boss(1240, 580); // Khởi tạo đối tượng boss

    public DoHoaGC() {
        setTitle("Gunny Game");
        setSize(1422, 810);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            // Load ảnh
            backGroundGC = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\BackGroundGaCon.jpg").getImage();
            DiaHinhGC = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\DiaHinhGaCon.png").getImage();
            DaoTrenKhongGC = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\DaoTrenKhong.png").getImage();
            TenMapGC = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\TenMap.png").getImage();
            playerImageGC = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\Boy.png").getImage(); 
            playerDeadImageGC = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\P_DIE.png").getImage();
            bossImageGC = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\BossGa.png").getImage(); 
            vongngam = new ImageIcon("D:\\Kunny_1\\Kunny_1\\src\\main\\java\\imagesGaCon\\VongTronNgam.png").getImage(); 
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading images: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Khởi tạo các nút
        JButton backBT = new JButton("<< Back");
        JButton powerBT = new JButton("Bộc phá");
        JButton flyBT = new JButton("Bay");
        JButton x2BT = new JButton("x2 Đạn");
        
        // Thiết lập vị trí và kích thước của các nút
        backBT.setBounds(20, 20, 100, 40);
        powerBT.setBounds(40, 180, 100, 40);
        flyBT.setBounds(40, 240, 100, 40);
        x2BT.setBounds(40, 300, 100, 40);
        
        // Thiết lập màu sắc cho các nút
        backBT.setBackground(Color.yellow);
        powerBT.setBackground(Color.red);
        flyBT.setBackground(Color.green);
        x2BT.setBackground(Color.yellow);
        
        // Thiết lập màu chữ cho các nút
        backBT.setForeground(Color.red);
        powerBT.setForeground(Color.yellow);
        flyBT.setForeground(Color.red);
        x2BT.setForeground(Color.red);
        
        // Thêm các nút vào JFrame
        add(backBT);
        add(powerBT);
        add(flyBT);
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
        
        // Vẽ ảnh nền
        g2d.drawImage(backGroundGC, 0, 0, getWidth(), getHeight(), this);

        // Thiết lập độ trong suốt của ảnh Địa Hình
        float alpha = 1f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(DaoTrenKhongGC, 222, 100, 1000, 500, this);
        // Vẽ ảnh Địa Hình
        g2d.drawImage(DiaHinhGC, 5, -75, 1400, 850, this);
       
        // Thiết lập độ trong suốt của ảnh Tên Map
        g2d.drawImage(TenMapGC, 670, 20, 150, 50, null);

        // vẽ vòng ngắm
        float alpha2 = 1f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2));
        g2d.drawImage(vongngam, player.getPlayerX() - 60, player.getPlayerY() - 70, 250, 250, this);
        
        // Vẽ player
        if (player.getIsPlayerAlive()) {
            if (player.getIsFacingLeft()) {
                // Nếu nhân vật đang hướng trái, phản chiếu hình ảnh
                g2d.drawImage(playerImageGC, player.getPlayerX() + 150, player.getPlayerY(), -150, 150, this);
            } else {
                // Nếu nhân vật đang hướng phải, vẽ bình thường
                g2d.drawImage(playerImageGC, player.getPlayerX(), player.getPlayerY(), 150, 150, this);
            }
        } else {
            g2d.drawImage(playerDeadImageGC, player.getPlayerX() - 80, player.getPlayerY() - 100, 300, 300, this);
        }

        
        // Vẽ thanh máu của player
        g2d.setColor(Color.GRAY);
        g2d.fillRect(player.getPlayerHealthBarX() - 1, player.getPlayerHealthBarY() - 1, player.getPlayerHealthBarWidth() + 2, player.getPlayerHealthBarHeight() + 2);

        // Tạo GradientPaint từ màu đỏ sang màu vàng dựa trên lượng máu còn lại của player
        GradientPaint playerGradient = new GradientPaint(player.getPlayerHealthBarX(), player.getPlayerHealthBarY(), Color.RED, player.getPlayerHealthBarX() + player.getPlayerHealthBarWidth(), player.getPlayerHealthBarY(), Color.YELLOW);
        g2d.setPaint(playerGradient);

        // Vẽ thanh máu của player với GradientPaint
        int playerHealthBarWidth = (int) ((double) player.getHealth() / player.getMaxHealth() * player.getPlayerHealthBarWidth());
        g2d.fillRect(player.getPlayerHealthBarX(), player.getPlayerHealthBarY(), playerHealthBarWidth, player.getPlayerHealthBarHeight());

        // Vẽ viền màu vàng xung quanh thanh máu của player
        g2d.setColor(Color.YELLOW);
        g2d.drawRect(player.getPlayerHealthBarX() - 2, player.getPlayerHealthBarY() - 2, player.getPlayerHealthBarWidth() + 2, player.getPlayerHealthBarHeight() + 2);

        // vẽ boss
        if (boss.getIsBossFacingLeft()) {
            // Nếu boss đang hướng trái, phản chiếu hình ảnh
            g2d.drawImage(bossImageGC, boss.getBossX() + 150, boss.getBossY(), -150, 150, this);
        } else {
            // Nếu nhân vật đang hướng phải, vẽ bình thường
            g2d.drawImage(bossImageGC, boss.getBossX(), boss.getBossY(), 150, 150, this);
        }
        // Tương tự cho thanh máu của boss
        g2d.setColor(Color.GRAY);
        g2d.fillRect(boss.getBossHealthBarX() - 1, boss.getBossHealthBarY() - 1, boss.getBossHealthBarWidth() + 2, boss.getBossHealthBarHeight() + 2);

        // Tạo GradientPaint từ màu đỏ sang màu vàng dựa trên lượng máu còn lại của boss
        GradientPaint bossGradient = new GradientPaint(boss.getBossHealthBarX(), boss.getBossHealthBarY(), Color.RED, boss.getBossHealthBarX() + boss.getBossHealthBarWidth(), boss.getBossHealthBarY(), Color.YELLOW);
        g2d.setPaint(bossGradient);

        // Vẽ thanh máu của boss với GradientPaint
        int bossHealthBarWidth = (int) ((double) boss.getBossHealth() / boss.getBossMaxHealth() * boss.getBossHealthBarWidth());
        g2d.fillRect(boss.getBossHealthBarX(), boss.getBossHealthBarY(), bossHealthBarWidth, boss.getBossHealthBarHeight());

        // Vẽ viền màu vàng xung quanh thanh máu của boss
        g2d.setColor(Color.YELLOW);
        g2d.drawRect(boss.getBossHealthBarX() - 2, boss.getBossHealthBarY() - 2, boss.getBossHealthBarWidth() + 2, boss.getBossHealthBarHeight() + 2);

        if (player.getHealth() <= 0) player.setIsPlayerAlive(false);
        // Giải phóng Graphics2D
        g2d.dispose();
    }

    // Xử lý sự kiện từ bàn phím
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Xử lý phím di chuyển cho player
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> {
                player.setPlayerX(player.getPlayerX() - player.getPlayerSpeed()); // Di chuyển sang trái
                player.setIsFacingLeft(true); // Đặt hướng di chuyển sang trái
            }
            case KeyEvent.VK_RIGHT -> {
                player.setPlayerX(player.getPlayerX() + player.getPlayerSpeed()); // Di chuyển sang phải
                player.setIsFacingLeft(false); // Đặt hướng di chuyển sang phải
            }
        }
        // Cập nhật vị trí của thanh máu
        player.setPlayerHealthBarX(player.getPlayerX() + 30);
        player.setPlayerHealthBarY(player.getPlayerY() - player.getPlayerHealthBarHeight() + 10);

        // Vẽ lại màn hình
        repaint();
        
    }

    // Các phương thức còn lại của KeyListener không cần xử lý
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE -> {
                boss.moveToPlayer(player.getPlayerX(), player.getPlayerY());
                boss.attackPlayer(player);
                System.out.println(player.getHealth());
                boss.moveBossToRandomPosition();
            }   
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DoHoaGC(); 
        });
    }
}
