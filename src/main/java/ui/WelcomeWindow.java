package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class WelcomeWindow extends JFrame {

    public WelcomeWindow() {
        setTitle("Вітаємо");
        setSize(400, 130);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setIconImage(createCustomIcon());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(240, 245, 250));

        JLabel welcomeText = new JLabel("Вітаємо вас у грі дитинства і всіх розумників!");
        welcomeText.setFont(new Font("SansSerif", Font.BOLD, 14));
        welcomeText.setForeground(new Color(50, 50, 50));

        JButton okButton = new JButton("ОК");
        okButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        okButton.setFocusPainted(false);

        okButton.addActionListener(_ -> {
            this.dispose();
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
        });

        panel.add(welcomeText);
        panel.add(okButton);
        add(panel, BorderLayout.CENTER);
    }

    private BufferedImage createCustomIcon() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, 32, 32);
        g2d.dispose();
        return img;
    }
}