package game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class GameEngine {
    private final Set<String> availableCities;
    private final Set<String> usedCities;
    private char lastLetter;

    public GameEngine() {
        this.availableCities = new HashSet<>();
        this.usedCities = new HashSet<>();
        this.lastLetter = '\0';
        loadCities();
    }

    private void loadCities() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("cities.txt");

            if (is == null) {
                System.err.println("Помилка: Файл cities.txt не знайдено у папці resources!");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        availableCities.add(line.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Помилка завантаження бази міст: " + e.getMessage());
        }
    }

    public String processPlayerMove(String city) {
        if ("здаюсь".equalsIgnoreCase(city)) {
            return "WIN_COMPUTER";
        }

        String normalizedCity = city.trim().toLowerCase();

        if (!availableCities.contains(normalizedCity) && !usedCities.contains(normalizedCity)) {
            return "Помилка: Я не знаю такого міста. Спробуйте інше.";
        }

        if (usedCities.contains(normalizedCity)) {
            return "Помилка: Це місто вже називали!";
        }

        if (lastLetter != '\0' && normalizedCity.charAt(0) != lastLetter) {
            return "Помилка: Місто має починатися на літеру '" + Character.toUpperCase(lastLetter) + "'.";
        }

        usedCities.add(normalizedCity);
        availableCities.remove(normalizedCity);

        char letterForComputer = getValidLastLetter(normalizedCity);

        return makeComputerMove(letterForComputer);
    }

    private String makeComputerMove(char startingLetter) {
        for (String city : availableCities) {
            if (city.charAt(0) == startingLetter) {
                usedCities.add(city);
                availableCities.remove(city);
                lastLetter = getValidLastLetter(city);

                return city.substring(0, 1).toUpperCase() + city.substring(1);
            }
        }
        return "WIN_PLAYER";
    }

    private char getValidLastLetter(String city) {
        for (int i = city.length() - 1; i >= 0; i--) {
            char c = city.charAt(i);
            if (c != 'ь' && c != 'и' && c != 'й' && c != 'ї') {
                return c;
            }
        }
        return city.charAt(city.length() - 1);
    }

    public int getScore() {
        return usedCities.size();
    }
}