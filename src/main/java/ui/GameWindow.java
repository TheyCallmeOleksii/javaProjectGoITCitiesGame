package ui;

import game.GameEngine;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class GameWindow extends JFrame {

    // Ворнінги 1-3 виправлено: додали слово final до цих трьох полів
    private final GameEngine engine;
    private final JTextField inputField;
    private final JLabel computerResponseLabel;
    private final JLabel scoreLabel;

    public GameWindow() {
        this.engine = new GameEngine();

        // Налаштування головного вікна
        setTitle("Міста");
        setSize(400, 500); // За ТЗ розмір ~400x500
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Центрування
        setResizable(false);
        setIconImage(createCustomIcon());

        // Головна панель з вертикальним вирівнюванням
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Рахунок гри
        scoreLabel = new JLabel("Рахунок: 0");
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Лейбл "Введіть назву міста"
        JLabel promptLabel = new JLabel("Введіть назву міста (або 'здаюсь'):");
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        promptLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Текстове поле для вводу
        inputField = new JTextField();
        inputField.setMaximumSize(new java.awt.Dimension(300, 35));
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Кнопка "Зробити хід"
        JButton moveButton = new JButton("Зробити хід");
        moveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        moveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        moveButton.setFocusPainted(false);

        // Лейбл відповіді комп'ютера
        computerResponseLabel = new JLabel("Комп'ютер: чекаю ваш хід...");
        computerResponseLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        computerResponseLabel.setForeground(new Color(0, 100, 0));
        computerResponseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        computerResponseLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Ворнінги 4-5 виправлено: замінили 'e' на '_'
        moveButton.addActionListener(_ -> processMove());
        inputField.addActionListener(_ -> processMove());

        // Збираємо вікно як конструктор
        mainPanel.add(scoreLabel);
        mainPanel.add(promptLabel);
        mainPanel.add(inputField);
        mainPanel.add(javax.swing.Box.createVerticalStrut(15)); // Відступ
        mainPanel.add(moveButton);
        mainPanel.add(computerResponseLabel);

        add(mainPanel);
    }

    private void processMove() {
        String city = inputField.getText().trim();
        if (city.isEmpty()) {
            return;
        }

        String result = engine.processPlayerMove(city);

        if ("WIN_COMPUTER".equals(result)) {
            showGameOverDialog("Комп'ютер переміг!", "Ви здалися. Рахунок: " + engine.getScore());
        } else if ("WIN_PLAYER".equals(result)) {
            showGameOverDialog("Ви перемогли!", "У комп'ютера закінчилися слова! Рахунок: " + engine.getScore());
        } else if (result.startsWith("Помилка")) {
            // Виводимо помилку червоним кольором
            computerResponseLabel.setForeground(Color.RED);
            computerResponseLabel.setText(result);
        } else {
            // Успішний хід комп'ютера
            computerResponseLabel.setForeground(new Color(0, 100, 0));
            computerResponseLabel.setText("Комп'ютер: " + result);
            scoreLabel.setText("Рахунок: " + engine.getScore());
            inputField.setText(""); // Очищаємо поле для наступного вводу
        }
    }

    private void showGameOverDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); // Завершуємо програму після закінчення гри
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