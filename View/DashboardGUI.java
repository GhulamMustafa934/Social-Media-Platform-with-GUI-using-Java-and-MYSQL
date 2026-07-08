package View;

import Model.User;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DashboardGUI {
    
    private User currentUser;
    
    public DashboardGUI(User user) {
        this.currentUser = user;
        
        JFrame frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!", 
            30, GUIConstants.blue, Font.BOLD);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.CENTER);
        
        // Logout button
        JButton logoutButton = new JButton("Logout", 30, 20);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(GUIConstants.lightGray);
        bottomPanel.add(logoutButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        logoutButton.addMouseListener(new java.awt.event.MouseListener() {
            @Override public void mouseReleased(java.awt.event.MouseEvent e) {}
            @Override public void mousePressed(java.awt.event.MouseEvent e) {}
            @Override public void mouseExited(java.awt.event.MouseEvent e) {}
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {}
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new LoginGUI(); // Go back to login
            }
        });
        
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }
}
