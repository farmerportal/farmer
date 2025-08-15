import javax.swing.*;
import java.awt.*;
import java.sql.*;
// Uncomment below line if using FlatLaf
 import com.formdev.flatlaf.FlatDarculaLaf;

public class LoginPage extends JFrame {
    private JTextField mobileField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    static final String URL = "jdbc:mysql://localhost:3306/farmer_portal";
    static final String USER = "root"; 
    static final String PASS = ""; // change as per your MySQL

    public LoginPage() { 
        setTitle("Farmer Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Mobile:"));
        mobileField = new JTextField();
        panel.add(mobileField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> loginFarmer());
        registerButton.addActionListener(e -> {
            new RegisterPage().setVisible(true);
            dispose();
        });
    }

    private void loginFarmer() {
        String mobile = mobileField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "SELECT * FROM farmers WHERE mobile = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, mobile);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String location = rs.getString("location"); // get location from DB

                JOptionPane.showMessageDialog(this, "Login Successful!");
                new DashboardPage(name, location).setVisible(true); // fixed spelling
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid mobile or password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Uncomment below line if FlatLaf is installed
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize Look and Feel");
        }

        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
