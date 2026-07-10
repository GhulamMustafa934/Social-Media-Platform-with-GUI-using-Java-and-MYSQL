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
        frame.setSize(550, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(GUIConstants.darkBg);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(GUIConstants.darkBg);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Title
        JLabel titleLabel = new JLabel("✏️ Edit Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(GUIConstants.textWhite);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBackground(GUIConstants.darkBg);

        // First Name
        JLabel l1 = new JLabel("First Name:");
        l1.setForeground(GUIConstants.textWhite);
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(l1);
        JTextField firstNameField = new JTextField(user.getFirstName());
        styleTextField(firstNameField);
        formPanel.add(firstNameField);

        // Last Name
        JLabel l2 = new JLabel("Last Name:");
        l2.setForeground(GUIConstants.textWhite);
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(l2);
        JTextField lastNameField = new JTextField(user.getLastName());
        styleTextField(lastNameField);
        formPanel.add(lastNameField);

        // Email
        JLabel l3 = new JLabel("Email:");
        l3.setForeground(GUIConstants.textWhite);
        l3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(l3);
        JTextField emailField = new JTextField(user.getEmail());
        styleTextField(emailField);
        formPanel.add(emailField);

        // New Password
        JLabel l4 = new JLabel("New Password:");
        l4.setForeground(GUIConstants.textWhite);
        l4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(l4);
        JPasswordField passwordField = new JPasswordField();
        styleTextField(passwordField);
        formPanel.add(passwordField);

        // Confirm Password
        JLabel l5 = new JLabel("Confirm Password:");
        l5.setForeground(GUIConstants.textWhite);
        l5.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(l5);
        JPasswordField confirmField = new JPasswordField();
        styleTextField(confirmField);
        formPanel.add(confirmField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(GUIConstants.darkBg);

        JButton saveButton = new JButton("💾 Save Changes");
        saveButton.setBackground(GUIConstants.btnPrimary);
        saveButton.setForeground(GUIConstants.textWhite);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("❌ Cancel");
        cancelButton.setBackground(GUIConstants.btnSecondary);
        cancelButton.setForeground(GUIConstants.textWhite);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Save Action
        saveButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(GUIConstants.btnPrimaryHover);
            }
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

    private void styleTextField(JTextField field) {
        field.setBackground(GUIConstants.darkCard);
        field.setForeground(GUIConstants.textWhite);
        field.setCaretColor(GUIConstants.textWhite);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    private void styleTextField(JPasswordField field) {
        field.setBackground(GUIConstants.darkCard);
        field.setForeground(GUIConstants.textWhite);
        field.setCaretColor(GUIConstants.textWhite);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }
}