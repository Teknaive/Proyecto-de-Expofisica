package Interfaces;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public interface GraphicalConfiguration {

    // Get the icon image from the specified path
    private Image getIconImage(String imagePath) {
        // Use the Toolkit class to get the image from the specified path
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(imagePath));
    }

    // Set the look and feel of the specified frame
    private void setLookAndFeel(JFrame frame, String lookAndFeelName) throws UnsupportedLookAndFeelException {
        try {
            // Set the Look and Feel to the specified one
            UIManager.setLookAndFeel(lookAndFeelName);
            // Update the component tree UI of the frame to apply the new Look and Feel
            SwingUtilities.updateComponentTreeUI(frame);
            // Pack the frame to apply the Look and Feel changes properly
            frame.pack();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Print stack trace in case of an exception
            e.printStackTrace(System.out);
        }
    }

    // Apply personal settings to the frame
    default void framePersonalSettings(JFrame frame, String frameTitle, int frameWidth, int frameHeight, String imagePath) throws UnsupportedLookAndFeelException {
        // Set the title of the frame
        frame.setTitle(frameTitle);
        // Set the icon image of the frame
        frame.setIconImage(getIconImage(imagePath));
        // Set the Look and Feel of the frame to Nimbus
        setLookAndFeel(frame, "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        // Set the size of the frame
        frame.setSize(frameWidth, frameHeight);
        // Prevent the frame from being resizable
        frame.setResizable(false);
    }
}
