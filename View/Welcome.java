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

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.add(new JLabel("Welcome", 40, GUIConstants.blue, Font.BOLD), BorderLayout.NORTH);
        
        JPanel center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setBackground(GUIConstants.lightGray);
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

        createAcc.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {
                String firstNameText = firstName.getText();
                String lastNameText = lastName.getText();
                String emailText = email.getText();
                String passwordText = password.getText();
                String confirmPasswordText = confirmPassword.getText();
                
                if (firstNameText.isEmpty() || firstNameText.equals("First Name")) {
                    new Alert("First Name cannot be empty", frame);
                    return;
                }
                
                if (lastNameText.isEmpty() || lastNameText.equals("Last Name")) {
                    new Alert("Last Name cannot be empty", frame);
                    return;
                }

                if (emailText.isEmpty() || emailText.equals("Email")) {
                    new Alert("Email cannot be empty", frame);
                    return;
                }

                if (passwordText.isEmpty() || passwordText.equals("Password")) {
                    new Alert("Password cannot be empty", frame);
                    return;
                }

                if (passwordText.length() < 6) {
                    new Alert("Password must contain at least 6 characters", frame);
                    return;
                }

                if (confirmPasswordText.isEmpty() || confirmPasswordText.equals("Confirm Password")) {
                    new Alert("Please confirm password", frame);
                    return;
                }

                if (!passwordText.equals(confirmPasswordText)) {
                    new Alert("Password doesn't match", frame);
                    return;
                }
                
                // ✅ SAVE TO DATABASE
                try {
                    CreateUser createUser = new CreateUser();  // ✅ No parameters needed
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

        center.add(createAcc);
        panel.add(center, BorderLayout.CENTER);

        JLabel login = new JLabel("Already have an account? Log in", 20, GUIConstants.black, Font.BOLD);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.setHorizontalAlignment(JLabel.CENTER);
        panel.add(login, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }
}