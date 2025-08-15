import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JFrame {
    public DashboardPage(String farmerName, String location) {
        setTitle("Farmer Dashboard");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel for layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + farmerName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Location message
        JLabel locationLabel = new JLabel("Your location: " + location, SwingConstants.CENTER);
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add labels to panel
        panel.add(welcomeLabel);
        panel.add(locationLabel);

        add(panel);
    }
}
