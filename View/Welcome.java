package View;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Welcome {

    public Welcome() {
        JFrame frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main panel with WHITE background
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.white);  // ✅ White background
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        
        // Title - "Welcome"
        JLabel titleLabel = new JLabel("Welcome", 40, GUIConstants.blue, Font.BOLD);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel with WHITE background
        JPanel center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setBackground(GUIConstants.white);  // ✅ White background
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));
        
        JTextField firstName = new JTextField("First Name");
        center.add(firstName);
        
        JTextField lastName = new JTextField("Last Name");
        center.add(lastName);
        
        JTextField email = new JTextField("Email");
        center.add(email);
        
        JTextField password = new JTextField("Password");
        center.add(password);
        
        JTextField confirmPassword = new JTextField("Confirm Password");
        center.add(confirmPassword);
        
        JButton createAcc = new JButton("Create Account", 45, 20);
        center.add(createAcc);

        panel.add(center, BorderLayout.CENTER);

        // Login link
        JLabel login = new JLabel("Already have an account? Log in", 20, GUIConstants.black, Font.BOLD);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.setHorizontalAlignment(JLabel.CENTER);
        panel.add(login, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }
}