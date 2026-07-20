package game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CityRepository {

    private final Set<String> allCities;

    public CityRepository() {
        Set<String> loadedCities = new HashSet<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cities.txt")) {

            if (is == null) {
                throw new RuntimeException("Файл cities.txt не знайдено у папці resources!");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        loadedCities.add(line.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Помилка завантаження бази міст: " + e.getMessage(), e);
        }

        this.allCities = Collections.unmodifiableSet(loadedCities);
    }

    public Set<String> getCitiesCopy() {
        return new HashSet<>(allCities);
    }
}