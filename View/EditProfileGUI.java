package View;

import Model.User;
import Controller.UpdateUser;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditProfileGUI {
    
    private User currentUser;
    private JFrame frame;
    
    public EditProfileGUI(User user) {
        this.currentUser = user;
        
        frame = new JFrame();
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(GUIConstants.lightGray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("✏️ Edit Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(GUIConstants.blue);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(GUIConstants.lightGray);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // First Name
        formPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField(user.getFirstName());
        formPanel.add(firstNameField);
        
        // Last Name
        formPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField(user.getLastName());
        formPanel.add(lastNameField);
        
        // Email
        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(user.getEmail());
        formPanel.add(emailField);
        
        // New Password
        formPanel.add(new JLabel("New Password:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);
        
        // Confirm Password
        formPanel.add(new JLabel("Confirm Password:"));
        JPasswordField confirmField = new JPasswordField();
        formPanel.add(confirmField);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout());
        buttonPanel.setBackground(GUIConstants.lightGray);
        
        JButton saveButton = new JButton("💾 Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        buttonPanel.add(saveButton);
        
        JButton cancelButton = new JButton("❌ Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Save Action
        saveButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirm = new String(confirmField.getPassword());
                
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    new Alert("Please fill all fields!", frame);
                    return;
                }
                
                if (!password.isEmpty() && !password.equals(confirm)) {
                    new Alert("Passwords don't match!", frame);
                    return;
                }
                
                UpdateUser updateUser = new UpdateUser();
                boolean success = updateUser.updateUser(
                    user.getID(),
                    firstName,
                    lastName,
                    email,
                    password.isEmpty() ? null : password
                );
                
                if (success) {
                    new Alert("Profile updated successfully!", frame);
                    frame.dispose();
                    new ProfileGUI(currentUser);
                } else {
                    new Alert("Failed to update profile!", frame);
                }
            }
        });
        
        // Cancel Action
        cancelButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new ProfileGUI(currentUser);
            }
        });
        
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.requestFocus();
    }
}