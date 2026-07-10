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

import Controller.CreateUser;

public class Welcome {

    public Welcome() {
        JFrame frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(GUIConstants.darkBg);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.darkBg);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.add(new JLabel("Welcome", 40, GUIConstants.accentBlue, Font.BOLD), BorderLayout.NORTH);
        
        JPanel center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setBackground(GUIConstants.darkBg);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));
        
        JTextField firstName = new JTextField("Enter your first name");
        styleTextField(firstName);
        center.add(firstName);
        
        JTextField lastName = new JTextField("Enter your last name");
        styleTextField(lastName);
        center.add(lastName);
        
        JTextField email = new JTextField("Enter your email");
        styleTextField(email);
        center.add(email);
        
        JTextField password = new JTextField("Enter your password");
        styleTextField(password);
        center.add(password);
        
        JTextField confirmPassword = new JTextField("Re-enter your password");
        styleTextField(confirmPassword);
        center.add(confirmPassword);
        
        // ✅ Create Account Button
        JButton createAcc = new JButton("Create Account", 45, 20);
        createAcc.setBackground(GUIConstants.btnPrimary);
        createAcc.setForeground(GUIConstants.textWhite);
        center.add(createAcc);
        
        panel.add(center, BorderLayout.CENTER);

        JLabel login = new JLabel("Already have an account? Log in", 20, GUIConstants.textGray, Font.BOLD);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.setHorizontalAlignment(JLabel.CENTER);

        login.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new LoginGUI();
            }
        });

        panel.add(login, BorderLayout.SOUTH);

        createAcc.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                createAcc.setBackground(GUIConstants.btnPrimaryHover);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String firstNameText = firstName.getText();
                String lastNameText = lastName.getText();
                String emailText = email.getText();
                String passwordText = password.getText();
                String confirmPasswordText = confirmPassword.getText();
                
                if (firstNameText.isEmpty() || firstNameText.equals("Enter your first name")) {
                    new Alert("First Name cannot be empty", frame);
                    return;
                }
                
                if (lastNameText.isEmpty() || lastNameText.equals("Enter your last name")) {
                    new Alert("Last Name cannot be empty", frame);
                    return;
                }

                if (emailText.isEmpty() || emailText.equals("Enter your email")) {
                    new Alert("Email cannot be empty", frame);
                    return;
                }

                if (passwordText.isEmpty() || passwordText.equals("Enter your password")) {
                    new Alert("Password cannot be empty", frame);
                    return;
                }

                if (passwordText.length() < 6) {
                    new Alert("Password must contain at least 6 characters", frame);
                    return;
                }

                if (confirmPasswordText.isEmpty() || confirmPasswordText.equals("Re-enter your password")) {
                    new Alert("Please confirm password", frame);
                    return;
                }

                if (!passwordText.equals(confirmPasswordText)) {
                    new Alert("Password doesn't match", frame);
                    return;
                }
                
                try {
                    CreateUser createUser = new CreateUser();
                    boolean success = createUser.registerUser(
                        firstNameText,
                        lastNameText,
                        emailText,
                        passwordText
                    );
                    
                    if (success) {
                        new Alert("Account Created Successfully!", frame);
                        System.out.println("✅ User saved to database!");
                    } else {
                        new Alert("Failed to create account!", frame);
                        System.out.println("❌ Failed to save user!");
                    }
                } catch (Exception ex) {
                    new Alert("Database error: " + ex.getMessage(), frame);
                    ex.printStackTrace();
                }
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