import game.CityRepository;
import ui.WelcomeWindow;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AppLauncher {

    private AppLauncher() {
    }

    @SuppressWarnings("unused")
    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                CityRepository repository = new CityRepository();
                repository.loadCities();

                WelcomeWindow welcomeWindow = new WelcomeWindow(repository);
                welcomeWindow.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage(),
                        "Критична помилка",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}