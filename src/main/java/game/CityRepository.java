package game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class CityRepository {

    public Set<String> loadCities() {
        Set<String> cities = new HashSet<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cities.txt")) {

            if (is == null) {
                throw new RuntimeException("Файл cities.txt не знайдено у папці resources!");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        cities.add(line.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Помилка завантаження бази міст: " + e.getMessage(), e);
        }
        return cities;
    }
}