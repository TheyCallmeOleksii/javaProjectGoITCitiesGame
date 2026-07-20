package game;

import java.util.HashSet;
import java.util.Set;

public class GameEngine {
    private final Set<String> availableCities;
    private final Set<String> usedCities;
    private char lastLetter;

    private int playerScore;
    private int computerScore;

    public GameEngine(CityRepository repository) {
        this.availableCities = repository.getCitiesCopy();
        this.usedCities = new HashSet<>();
        this.lastLetter = '\0';
        this.playerScore = 0;
        this.computerScore = 0;
    }

    public MoveResult processPlayerMove(String city) {
        if (city == null || city.trim().isEmpty()) {
            return new MoveResult(MoveStatus.EMPTY_INPUT, "Введіть назву міста.");
        }

        if ("здаюсь".equalsIgnoreCase(city.trim())) {
            return new MoveResult(MoveStatus.COMPUTER_WON, "Ви здалися.");
        }

        String normalizedCity = city.trim().toLowerCase();

        if (!availableCities.contains(normalizedCity) && !usedCities.contains(normalizedCity)) {
            return new MoveResult(MoveStatus.UNKNOWN_CITY, "Я не знаю такого міста. Спробуйте інше.");
        }

        if (usedCities.contains(normalizedCity)) {
            return new MoveResult(MoveStatus.ALREADY_USED, "Це місто вже називали!");
        }

        if (lastLetter != '\0' && normalizedCity.charAt(0) != lastLetter) {
            return new MoveResult(MoveStatus.WRONG_LETTER, "Місто має починатися на літеру '" + Character.toUpperCase(lastLetter) + "'.");
        }

        usedCities.add(normalizedCity);
        availableCities.remove(normalizedCity);
        playerScore++;

        char letterForComputer = getValidLastLetter(normalizedCity);
        return makeComputerMove(letterForComputer);
    }

    private MoveResult makeComputerMove(char startingLetter) {
        for (String city : availableCities) {
            if (city.charAt(0) == startingLetter) {
                usedCities.add(city);
                availableCities.remove(city);
                computerScore++;
                lastLetter = getValidLastLetter(city);

                String formattedCity = city.substring(0, 1).toUpperCase() + city.substring(1);
                return new MoveResult(MoveStatus.SUCCESS, formattedCity);
            }
        }
        return new MoveResult(MoveStatus.PLAYER_WON, "У мене закінчилися слова!");
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

    public int getPlayerScore() {
        return playerScore;
    }

    public int getComputerScore() {
        return computerScore;
    }
}