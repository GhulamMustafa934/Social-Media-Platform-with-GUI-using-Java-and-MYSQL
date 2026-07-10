package View;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Controller.LoginUser;
import Model.User;

public class LoginGUI {

    public LoginGUI() {
        JFrame frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(GUIConstants.darkBg);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.darkBg);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));

        JLabel titleLabel = new JLabel("Login", 40, GUIConstants.accentBlue, Font.BOLD);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
        center.setBackground(GUIConstants.darkBg);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));

        // Email field
        JTextField emailField = new JTextField("Enter your email");
        styleTextField(emailField);
        center.add(emailField);

        // Password field
        JTextField passwordField = new JTextField("Enter your password");
        styleTextField(passwordField);
        center.add(passwordField);

        // ✅ Login Button
        JButton loginButton = new JButton("Login", 45, 20);
        loginButton.setBackground(GUIConstants.btnPrimary);
        loginButton.setForeground(GUIConstants.textWhite);
        center.add(loginButton);

        panel.add(center, BorderLayout.CENTER);

        JLabel registerLink = new JLabel("Don't have an account? Register", 20, GUIConstants.textGray, Font.BOLD);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(registerLink, BorderLayout.SOUTH);

        // Login Button Action
        loginButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(GUIConstants.btnPrimaryHover);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String email = emailField.getText();
                String password = passwordField.getText();

                if (email.isEmpty() || email.equals("Enter your email")) {
                    new Alert("Please enter email", frame);
                    return;
                }

                if (password.isEmpty() || password.equals("Enter your password")) {
                    new Alert("Please enter password", frame);
                    return;
                }

                try {
                    LoginUser loginUser = new LoginUser();
                    User user = loginUser.loginUser(email, password);

                    if (user != null) {
                        new Alert("Login Successful! Welcome " + user.getFirstName(), frame);
                        frame.dispose();
                        new DashboardGUI(user);
                    } else {
                        new Alert("Invalid email or password!", frame);
                    }
                } catch (Exception ex) {
                    new Alert("Database error: " + ex.getMessage(), frame);
                    ex.printStackTrace();
                }
            }
        });

        registerLink.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new Welcome();
            }
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }

    private void styleTextField(JTextField field) {
        field.setBackground(GUIConstants.darkCard);
        field.setForeground(GUIConstants.textGray);
        field.setCaretColor(GUIConstants.textWhite);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }
}