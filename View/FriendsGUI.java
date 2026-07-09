package View;

import Model.User;
import Controller.FriendController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FriendsGUI {
    
    private User currentUser;
    private JFrame frame;
    private FriendController friendController;
    private JPanel friendsPanel;
    private JPanel requestsPanel;
    private JPanel searchPanel;
    
    public FriendsGUI(User user) {
        this.currentUser = user;
        this.friendController = new FriendController();
        
        frame = new JFrame();
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(GUIConstants.lightGray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("👥 Friends", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(GUIConstants.blue);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Tab Panel
        JPanel tabPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        tabPanel.setBackground(GUIConstants.lightGray);
        
        JButton friendsTab = new JButton("👥 Friends");
        JButton requestsTab = new JButton("📨 Requests");
        JButton searchTab = new JButton("🔍 Search");
        
        tabPanel.add(friendsTab);
        tabPanel.add(requestsTab);
        tabPanel.add(searchTab);
        
        mainPanel.add(tabPanel, BorderLayout.NORTH);
        
        // Content Panel
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(GUIConstants.lightGray);
        
        // Friends Panel
        friendsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        friendsPanel.setBackground(GUIConstants.white);
        JScrollPane friendsScroll = new JScrollPane(friendsPanel);
        friendsScroll.setBorder(BorderFactory.createTitledBorder("Your Friends"));
        contentPanel.add(friendsScroll, "friends");
        
        // Requests Panel
        requestsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        requestsPanel.setBackground(GUIConstants.white);
        JScrollPane requestsScroll = new JScrollPane(requestsPanel);
        requestsScroll.setBorder(BorderFactory.createTitledBorder("Friend Requests"));
        contentPanel.add(requestsScroll, "requests");
        
        // Search Panel
        searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(GUIConstants.white);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Users"));
        
        JPanel searchBar = new JPanel(new BorderLayout(5, 5));
        searchBar.setBackground(GUIConstants.white);
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("🔍 Search");
        searchBar.add(searchField, BorderLayout.CENTER);
        searchBar.add(searchButton, BorderLayout.EAST);
        searchPanel.add(searchBar, BorderLayout.NORTH);
        
        JPanel searchResults = new JPanel(new GridLayout(0, 1, 5, 5));
        searchResults.setBackground(GUIConstants.white);
        JScrollPane searchScroll = new JScrollPane(searchResults);
        searchPanel.add(searchScroll, BorderLayout.CENTER);
        
        contentPanel.add(searchPanel, "search");
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Back Button
        JButton backButton = new JButton("⬅ Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new DashboardGUI(currentUser);
            }
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);
        
        // Load data
        loadFriends();
        loadRequests();
        
        // Tab actions
        friendsTab.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                ((CardLayout) contentPanel.getLayout()).show(contentPanel, "friends");
                loadFriends();
            }
        });
        
        requestsTab.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                ((CardLayout) contentPanel.getLayout()).show(contentPanel, "requests");
                loadRequests();
            }
        });
        
        searchTab.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                ((CardLayout) contentPanel.getLayout()).show(contentPanel, "search");
            }
        });
        
        // Search action
        searchButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                String keyword = searchField.getText();
                if (!keyword.isEmpty()) {
                    performSearch(searchResults, keyword);
                }
            }
        });
        
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.requestFocus();
    }
    
    private void loadFriends() {
        friendsPanel.removeAll();
        ArrayList<User> friends = friendController.getFriends(currentUser.getID());
        
        if (friends.isEmpty()) {
            JLabel emptyLabel = new JLabel("No friends yet. Search for people to add!");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            friendsPanel.add(emptyLabel);
        } else {
            for (User friend : friends) {
                friendsPanel.add(createFriendCard(friend));
            }
        }
        
        friendsPanel.revalidate();
        friendsPanel.repaint();
    }
    
    private void loadRequests() {
        requestsPanel.removeAll();
        ArrayList<User> requests = friendController.getPendingRequests(currentUser.getID());
        
        if (requests.isEmpty()) {
            JLabel emptyLabel = new JLabel("No friend requests!");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            requestsPanel.add(emptyLabel);
        } else {
            for (User requester : requests) {
                requestsPanel.add(createRequestCard(requester));
            }
        }
        
        requestsPanel.revalidate();
        requestsPanel.repaint();
    }
    
    private JPanel createFriendCard(User friend) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(GUIConstants.white);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel nameLabel = new JLabel("👤 " + friend.getFirstName() + " " + friend.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(nameLabel, BorderLayout.WEST);
        
        JButton removeBtn = new JButton("❌ Remove");
        removeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(removeBtn, BorderLayout.EAST);
        
        removeBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = javax.swing.JOptionPane.showConfirmDialog(frame, "Remove " + friend.getFirstName() + " from friends?", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
                if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                    friendController.removeFriend(currentUser.getID(), friend.getID());
                    loadFriends();
                }
            }
        });
        
        return card;
    }
    
    private JPanel createRequestCard(User requester) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(GUIConstants.white);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel nameLabel = new JLabel("👤 " + requester.getFirstName() + " " + requester.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(nameLabel, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(GUIConstants.white);
        
        JButton acceptBtn = new JButton("✅ Accept");
        acceptBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(acceptBtn);
        
        JButton rejectBtn = new JButton("❌ Reject");
        rejectBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(rejectBtn);
        
        card.add(buttonPanel, BorderLayout.EAST);
        
        acceptBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                friendController.acceptRequest(currentUser.getID(), requester.getID());
                loadRequests();
                loadFriends();
            }
        });
        
        rejectBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                friendController.rejectRequest(currentUser.getID(), requester.getID());
                loadRequests();
            }
        });
        
        return card;
    }
    
    private void performSearch(JPanel resultsPanel, String keyword) {
        resultsPanel.removeAll();
        ArrayList<User> results = friendController.searchUsers(keyword, currentUser.getID());
        
        if (results.isEmpty()) {
            JLabel emptyLabel = new JLabel("No users found!");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            resultsPanel.add(emptyLabel);
        } else {
            for (User user : results) {
                resultsPanel.add(createSearchResultCard(user));
            }
        }
        
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private JPanel createSearchResultCard(User user) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(GUIConstants.white);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel nameLabel = new JLabel("👤 " + user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(nameLabel, BorderLayout.WEST);
        
        String status = friendController.getFriendStatus(currentUser.getID(), user.getID());
        JButton actionBtn = new JButton();
        
        if (status.equals("accepted")) {
            actionBtn.setText("✅ Friends");
            actionBtn.setEnabled(false);
        } else if (status.equals("pending")) {
            actionBtn.setText("⏳ Pending");
            actionBtn.setEnabled(false);
        } else {
            actionBtn.setText("➕ Add Friend");
        }
        
        card.add(actionBtn, BorderLayout.EAST);
        
        if (!status.equals("accepted") && !status.equals("pending")) {
            actionBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    friendController.sendRequest(currentUser.getID(), user.getID());
                    new Alert("Friend request sent to " + user.getFirstName() + "!", frame);
                    actionBtn.setText("⏳ Pending");
                    actionBtn.setEnabled(false);
                }
            });
        }
        
        return card;
    }
}