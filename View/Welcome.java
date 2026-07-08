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

public class Welcome {

    public Welcome() {
        JFrame frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ✅ Main panel with LIGHT GRAY background
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.add(new JLabel("Welcome", 40, GUIConstants.blue, Font.BOLD), BorderLayout.NORTH);
        
        // ✅ Center panel with LIGHT GRAY background
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
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // ✅ FIXED: Added .getText() to all fields
                if (firstName.getText().isEmpty() || firstName.getText().equals("First Name")) {
                    new Alert("First Name cannot be empty", frame);
                    return;
                }
                
                if (lastName.getText().isEmpty() || lastName.getText().equals("Last Name")) {
                    new Alert("Last Name cannot be empty", frame);
                    return;
                }

                if (email.getText().isEmpty() || email.getText().equals("Email")) {
                    new Alert("Email cannot be empty", frame);
                    return;
                }

                if (password.getText().isEmpty() || password.getText().equals("Password")) {
                    new Alert("Password cannot be empty", frame);
                    return;
                }

                if (password.getText().length() < 6) {
                    new Alert("Password must contain at least 6 characters", frame);
                    return;
                }

                if (confirmPassword.getText().isEmpty() || confirmPassword.getText().equals("Confirm Password")) {
                    new Alert("Please confirm password", frame);
                    return;
                }

                if (!password.getText().equals(confirmPassword.getText())) {
                    new Alert("Password doesn't match", frame);
                    return;
                }
                
                // Success
                new Alert("Account Created Successfully!", frame);
                System.out.println("Account Created Successfully!");
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