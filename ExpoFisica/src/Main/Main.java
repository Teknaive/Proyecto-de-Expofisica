package Main;

import Views.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {
        // Create and display the login window
        java.awt.EventQueue.invokeLater(() -> {
            try {
                Login login = new Login();
                login.setVisible(true);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
