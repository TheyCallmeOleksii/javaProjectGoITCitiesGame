package ui;

import game.CityRepository;
import game.GameEngine;
import game.MoveResult;

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

public class GameWindow extends JFrame {

    private final GameEngine engine;
    private final CityRepository repository;
    private final JTextField inputField;
    private final JLabel computerResponseLabel;
    private final JLabel scoreLabel;

    public GameWindow(CityRepository repository) {
        this.repository = repository;
        this.engine = new GameEngine(repository);

        setTitle("Міста");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(IconUtils.createCustomIcon());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        scoreLabel = new JLabel();
        updateScoreLabel();
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel promptLabel = new JLabel("Введіть назву міста (або 'здаюсь'):");
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        promptLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        inputField = new JTextField();
        inputField.setMaximumSize(new java.awt.Dimension(300, 35));
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton moveButton = new JButton("Зробити хід");
        moveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        moveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        moveButton.setFocusPainted(false);

        computerResponseLabel = new JLabel("Комп'ютер: чекаю ваш хід...");
        computerResponseLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        computerResponseLabel.setForeground(new Color(0, 100, 0));
        computerResponseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        computerResponseLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        moveButton.addActionListener(_ -> processMove());
        inputField.addActionListener(_ -> processMove());

        mainPanel.add(scoreLabel);
        mainPanel.add(promptLabel);
        mainPanel.add(inputField);
        mainPanel.add(javax.swing.Box.createVerticalStrut(15));
        mainPanel.add(moveButton);
        mainPanel.add(computerResponseLabel);

        add(mainPanel);
    }

    private void processMove() {
        String city = inputField.getText().trim();
        MoveResult result = engine.processPlayerMove(city);

        switch (result.status()) {
            case EMPTY_INPUT, UNKNOWN_CITY, ALREADY_USED, WRONG_LETTER -> {
                computerResponseLabel.setForeground(Color.RED);
                computerResponseLabel.setText("Помилка: " + result.message());
            }
            case SUCCESS -> {
                computerResponseLabel.setForeground(new Color(0, 100, 0));
                computerResponseLabel.setText("Комп'ютер: " + result.message());
                updateScoreLabel();
                inputField.setText("");
            }
            case COMPUTER_WON -> showGameOverDialog("Комп'ютер переміг!", result.message());
            case PLAYER_WON -> {
                updateScoreLabel();
                showGameOverDialog("Ви перемогли!", result.message());
            }
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText(String.format("Рахунок: Ви %d - %d Комп'ютер", engine.getPlayerScore(), engine.getComputerScore()));
    }

    private void showGameOverDialog(String title, String message) {
        String finalScore = String.format("\nФінальний рахунок: Ви %d - %d Комп'ютер", engine.getPlayerScore(), engine.getComputerScore());
        JOptionPane.showMessageDialog(this, message + finalScore, title, JOptionPane.INFORMATION_MESSAGE);

        this.dispose();
        new WelcomeWindow(repository).setVisible(true);
    }
}