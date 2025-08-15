import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterPage extends JFrame {
    private JTextField nameField, locationField, mobileField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    static final String URL = "jdbc:mysql://localhost:3306/farmer_portal";
    static final String USER = "root";
    static final String PASS = ""; // change this

    public RegisterPage() {
        setTitle("Farmer Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Location:"));
        locationField = new JTextField();
        panel.add(locationField);

        panel.add(new JLabel("Mobile:"));
        mobileField = new JTextField();
        panel.add(mobileField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        registerButton.addActionListener(e -> registerFarmer());
        backButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });
    }

    private void registerFarmer() {
        String name = nameField.getText();
        String location = locationField.getText();
        String mobile = mobileField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || location.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "INSERT INTO farmers(name, location, mobile, password) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, location);
            pst.setString(3, mobile);
            pst.setString(4, password);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            new LoginPage().setVisible(true);
            dispose();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Mobile number already registered.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

