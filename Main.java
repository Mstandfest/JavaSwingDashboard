package Dashboard;

import javax.swing.*;
import java.util.logging.*;

public class Main {
    static void main() {
        final Logger LOGGER = Logger.getLogger(Main.class.getName());
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Das Nimbus Look and Feel konnte nicht geladen werden.", e);
        }

        SwingUtilities.invokeLater(() -> {
            Dashboard meinDashboard = new Dashboard();
            meinDashboard.setVisible(true);
        });
    }
}
