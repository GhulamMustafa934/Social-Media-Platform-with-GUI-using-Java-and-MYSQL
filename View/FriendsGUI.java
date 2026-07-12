package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import Controller.FriendController;
import Model.User;

public class FriendsGUI {

    private User currentUser;
    private JFrame frame;
    private FriendController friendController;
    private JPanel listPanel;
    private JPanel contentPanel;
    private JButton peopleTab, requestsTab;

    public FriendsGUI(User user) {
        this.currentUser = user;
        this.friendController = new FriendController();

        frame = new JFrame();
        frame.setTitle("Social Media Platform");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(GUIConstants.darkBg);

        // ========== HEADER: title + home icon ==========
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(GUIConstants.darkBg);

        JLabel titleLabel = new JLabel("Friends");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(GUIConstants.textWhite);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JLabel homeIcon = new JLabel("Home", SwingConstants.CENTER);
        homeIcon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        homeIcon.setForeground(GUIConstants.accentBlue);
        homeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
        headerPanel.add(homeIcon, BorderLayout.EAST);

        homeIcon.addMouseListener(new MouseListener() {
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

        // ========== TOOLBAR: search + tabs ==========
        JPanel toolbar = new JPanel(new BorderLayout(10, 10));
        toolbar.setBackground(GUIConstants.darkBg);

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setForeground(GUIConstants.textWhite);
        searchField.setBackground(GUIConstants.darkCard);
        searchField.setCaretColor(GUIConstants.textWhite);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JPanel tabsPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        tabsPanel.setBackground(GUIConstants.darkBg);

        peopleTab = new JButton("People");
        requestsTab = new JButton("Requests");
        styleTab(peopleTab, true);
        styleTab(requestsTab, false);
        tabsPanel.add(peopleTab);
        tabsPanel.add(requestsTab);

        JPanel searchRow = new JPanel(new BorderLayout(0, 8));
        searchRow.setBackground(GUIConstants.darkBg);
        searchRow.add(searchField, BorderLayout.CENTER);

        toolbar.add(searchRow, BorderLayout.NORTH);
        toolbar.add(tabsPanel, BorderLayout.SOUTH);

        JPanel topArea = new JPanel(new BorderLayout(0, 12));
        topArea.setBackground(GUIConstants.darkBg);
        topArea.add(headerPanel, BorderLayout.NORTH);
        topArea.add(toolbar, BorderLayout.SOUTH);

        // ========== LIST ==========
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(GUIConstants.darkBg);

        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0, 1, 0, 8));
        listPanel.setBackground(GUIConstants.darkBg);
        JScrollPane listScroll = new JScrollPane(listPanel);
        listScroll.setBorder(BorderFactory.createEmptyBorder());
        listScroll.getViewport().setBackground(GUIConstants.darkBg);
        listScroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(listScroll, "people");

        JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new GridLayout(0, 1, 0, 8));
        requestsPanel.setBackground(GUIConstants.darkBg);
        JScrollPane requestsScroll = new JScrollPane(requestsPanel);
        requestsScroll.setBorder(BorderFactory.createEmptyBorder());
        requestsScroll.getViewport().setBackground(GUIConstants.darkBg);
        contentPanel.add(requestsScroll, "requests");

        // ========== FINAL LAYOUT ==========
        JPanel root = new JPanel(new BorderLayout(0, 15));
        root.setBackground(GUIConstants.darkBg);
        root.add(topArea, BorderLayout.NORTH);
        root.add(contentPanel, BorderLayout.CENTER);

        frame.getContentPane().add(root);

        // Load data
        loadPeople(listPanel, "");
        loadRequests(requestsPanel);

        // Tab switching
        peopleTab.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                styleTab(peopleTab, true);
                styleTab(requestsTab, false);
                ((CardLayout) contentPanel.getLayout()).show(contentPanel, "people");
            }
        });

        requestsTab.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                styleTab(peopleTab, false);
                styleTab(requestsTab, true);
                loadRequests(requestsPanel);
                ((CardLayout) contentPanel.getLayout()).show(contentPanel, "requests");
            }
        });

        // Search-as-you-type-ish: trigger on Enter
        searchField.addActionListener(e -> loadPeople(listPanel, searchField.getText().trim()));

        frame.setVisible(true);
        frame.requestFocus();
    }

    private void styleTab(JButton btn, boolean active) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        if (active) {
            btn.setBackground(GUIConstants.btnPrimary);
            btn.setForeground(java.awt.Color.WHITE);
        } else {
            btn.setBackground(GUIConstants.darkCard);
            btn.setForeground(GUIConstants.textWhite);
        }
    }

    private void loadPeople(JPanel panel, String keyword) {
        panel.removeAll();
        ArrayList<User> users = friendController.searchUsers(keyword, currentUser.getID());

        if (users.isEmpty()) {
            JLabel emptyLabel = new JLabel("No users found.");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setForeground(GUIConstants.textGray);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(emptyLabel);
        } else {
            for (User u : users) {
                panel.add(createUserRow(u));
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createUserRow(User user) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(GUIConstants.darkCard);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(GUIConstants.textWhite);
        row.add(nameLabel, BorderLayout.WEST);

        String status = friendController.getFriendStatus(currentUser.getID(), user.getID());
        JButton actionBtn = new JButton();
        actionBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        actionBtn.setFocusPainted(false);
        actionBtn.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));

        if (status.equals("accepted")) {
            actionBtn.setText("Friends");
            actionBtn.setBackground(GUIConstants.btnSecondary);
            actionBtn.setForeground(GUIConstants.textWhite);
            actionBtn.setEnabled(false);
        } else if (status.equals("pending")) {
            actionBtn.setText("Pending");
            actionBtn.setBackground(GUIConstants.btnSecondary);
            actionBtn.setForeground(GUIConstants.textGray);
            actionBtn.setEnabled(false);
        } else {
            actionBtn.setText("Follow");
            actionBtn.setBackground(GUIConstants.btnPrimary);
            actionBtn.setForeground(java.awt.Color.WHITE);
            actionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            actionBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {
                    actionBtn.setBackground(GUIConstants.btnPrimary);
                }
                @Override public void mouseEntered(MouseEvent e) {
                    actionBtn.setBackground(GUIConstants.btnPrimaryHover);
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    friendController.sendRequest(currentUser.getID(), user.getID());
                    new Alert("Friend request sent to " + user.getFirstName() + "!", frame);
                    actionBtn.setText("Pending");
                    actionBtn.setBackground(GUIConstants.btnSecondary);
                    actionBtn.setForeground(GUIConstants.textGray);
                    actionBtn.setEnabled(false);
                }
            });
        }

        row.add(actionBtn, BorderLayout.EAST);
        return row;
    }

    private void loadRequests(JPanel panel) {
        panel.removeAll();
        ArrayList<User> requests = friendController.getPendingRequests(currentUser.getID());

        if (requests.isEmpty()) {
            JLabel emptyLabel = new JLabel("No friend requests.");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setForeground(GUIConstants.textGray);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(emptyLabel);
        } else {
            for (User requester : requests) {
                panel.add(createRequestRow(panel, requester));
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createRequestRow(JPanel parentPanel, User requester) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(GUIConstants.darkCard);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel nameLabel = new JLabel(requester.getFirstName() + " " + requester.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(GUIConstants.textWhite);
        row.add(nameLabel, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(GUIConstants.darkCard);

        JButton acceptBtn = new JButton("Accept");
        acceptBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        acceptBtn.setBackground(GUIConstants.btnPrimary);
        acceptBtn.setForeground(java.awt.Color.WHITE);
        acceptBtn.setFocusPainted(false);
        acceptBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        acceptBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnPanel.add(acceptBtn);

        JButton rejectBtn = new JButton("Reject");
        rejectBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rejectBtn.setBackground(GUIConstants.btnSecondary);
        rejectBtn.setForeground(GUIConstants.accentRed);
        rejectBtn.setFocusPainted(false);
        rejectBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rejectBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnPanel.add(rejectBtn);

        row.add(btnPanel, BorderLayout.EAST);

        acceptBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                friendController.acceptRequest(currentUser.getID(), requester.getID());
                loadRequests(parentPanel);
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
                loadRequests(parentPanel);
            }
        });

        return row;
    }
}
