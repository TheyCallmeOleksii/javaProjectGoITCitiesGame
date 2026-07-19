package ui;

import game.CityRepository;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

public class WelcomeWindow extends JFrame {

    public WelcomeWindow(CityRepository repository) {
        setTitle("Вітаємо");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setIconImage(IconUtils.createCustomIcon());

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
            new GameWindow(repository).setVisible(true);
        });

        panel.add(welcomeText);
        panel.add(okButton);
        add(panel, BorderLayout.CENTER);
    }
}