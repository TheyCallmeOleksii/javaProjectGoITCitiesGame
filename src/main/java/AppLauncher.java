import ui.WelcomeWindow;

import javax.swing.SwingUtilities;

@SuppressWarnings("ALL")
public class AppLauncher {

    private AppLauncher() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeWindow welcomeWindow = new WelcomeWindow();
            welcomeWindow.setVisible(true);
        });
    }
}