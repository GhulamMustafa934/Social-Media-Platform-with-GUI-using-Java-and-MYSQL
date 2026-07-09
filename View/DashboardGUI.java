package View;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Controller.CreatePost;
import Controller.LikePost;
import Model.Post;
import Model.User;

public class DashboardGUI {
    
    private User currentUser;
    private JPanel feedPanel;
    private JFrame frame;
    
    public DashboardGUI(User user) {
        this.currentUser = user;
        
        frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(GUIConstants.lightGray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top Panel: Welcome + Logout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(GUIConstants.lightGray);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(GUIConstants.blue);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        topPanel.add(logoutButton, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(GUIConstants.lightGray);
        
        // Create Post Section
        JPanel postPanel = new JPanel(new BorderLayout(5, 5));
        postPanel.setBackground(GUIConstants.white);
        postPanel.setBorder(BorderFactory.createTitledBorder("Create Post"));
        
        JTextArea postArea = new JTextArea(3, 30);
        postArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        postArea.setLineWrap(true);
        postArea.setWrapStyleWord(true);
        postArea.setText("What's on your mind?");
        
        JButton postButton = new JButton("Post");
        postButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        postPanel.add(new JScrollPane(postArea), BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.EAST);
        
        centerPanel.add(postPanel, BorderLayout.NORTH);
        
        // Feed Section
        feedPanel = new JPanel();
        feedPanel.setLayout(new GridLayout(0, 1, 5, 5));
        feedPanel.setBackground(GUIConstants.white);
        
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Feed"));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Load Feed
        loadFeed();
        
        // Logout Action
        logoutButton.addMouseListener(new MouseListener() {
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
        
        // Post Button Action
        postButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseClicked(MouseEvent e) {
                String content = postArea.getText();
                if (content.isEmpty() || content.equals("What's on your mind?")) {
                    new Alert("Please write something!", frame);
                    return;
                }
                
                Post post = new Post(content, currentUser);
                CreatePost createPost = new CreatePost();
                int postId = createPost.savePost(post);
                
                if (postId > 0) {
                    new Alert("Post created successfully!", frame);
                    postArea.setText("What's on your mind?");
                    loadFeed();
                } else {
                    new Alert("Failed to create post!", frame);
                }
            }
        });
        
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.requestFocus();
    }
    
    private void loadFeed() {
        try {
            feedPanel.removeAll();
            CreatePost createPost = new CreatePost();
            LikePost likePost = new LikePost();
            java.util.ArrayList<Post> posts = createPost.getAllPosts();
            
            if (posts.isEmpty()) {
                JLabel emptyLabel = new JLabel("No posts yet. Be the first to post!");
                emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                feedPanel.add(emptyLabel);
            } else {
                for (Post post : posts) {
                    feedPanel.add(createPostCard(post, likePost));
                }
            }
            
            feedPanel.revalidate();
            feedPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel createPostCard(Post post, LikePost likePost) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(GUIConstants.white);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Header: User name
        JLabel userLabel = new JLabel("👤 " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(GUIConstants.black);
        card.add(userLabel, BorderLayout.NORTH);
        
        // Content
        JTextArea contentArea = new JTextArea(post.getContent());
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(GUIConstants.white);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(contentArea, BorderLayout.CENTER);
        
        // Bottom: Like button + counts
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(GUIConstants.white);
        
        int postId = post.getId();
        int likesCount = likePost.getLikesCount(postId);
        
        JLabel likesLabel = new JLabel("❤️ " + likesCount + " likes");
        likesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        likesLabel.setForeground(GUIConstants.black);
        bottomPanel.add(likesLabel, BorderLayout.WEST);
        
        boolean hasLiked = likePost.hasLiked(postId, currentUser.getID());
        
        JButton likeButton = new JButton(hasLiked ? "Unlike" : "Like");
        likeButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        likeButton.setForeground(hasLiked ? GUIConstants.blue : GUIConstants.black);
        
        likeButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseClicked(MouseEvent e) {
                int userId = currentUser.getID();
                boolean success;
                
                if (hasLiked) {
                    success = likePost.removeLike(postId, userId);
                } else {
                    success = likePost.addLike(postId, userId);
                }
                
                if (success) {
                    loadFeed();
                } else {
                    new Alert("Failed to update like!", frame);
                }
            }
        });
        
        bottomPanel.add(likeButton, BorderLayout.EAST);
        card.add(bottomPanel, BorderLayout.SOUTH);
        
        return card;
    }
}