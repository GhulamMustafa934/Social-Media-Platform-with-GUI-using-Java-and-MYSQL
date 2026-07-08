package View;

import Model.Post;
import Model.User;
import Controller.CreatePost;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class DashboardGUI {
    
    private User currentUser;
    private JTextArea feedArea;
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
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!", 
            24, GUIConstants.blue, Font.BOLD);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JButton logoutButton = new JButton("Logout", 30, 15);
        topPanel.add(logoutButton, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center Panel: Create Post + Feed
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
        
        JButton postButton = new JButton("Post", 30, 15);
        
        postPanel.add(new JScrollPane(postArea), BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.EAST);
        
        centerPanel.add(postPanel, BorderLayout.NORTH);
        
        // Feed Section
        JPanel feedPanel = new JPanel(new BorderLayout());
        feedPanel.setBackground(GUIConstants.white);
        feedPanel.setBorder(BorderFactory.createTitledBorder("Feed"));
        
        feedArea = new JTextArea(10, 30);
        feedArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        feedArea.setEditable(false);
        feedArea.setLineWrap(true);
        feedArea.setWrapStyleWord(true);
        
        feedPanel.add(new JScrollPane(feedArea), BorderLayout.CENTER);
        
        centerPanel.add(feedPanel, BorderLayout.CENTER);
        
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
                boolean success = createPost.savePost(post);
                
                if (success) {
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
            CreatePost createPost = new CreatePost();
            java.util.ArrayList<Post> posts = createPost.getAllPosts();
            
            StringBuilder feed = new StringBuilder();
            for (Post post : posts) {
                feed.append("👤 ").append(post.getUser().getFirstName())
                    .append(" ").append(post.getUser().getLastName())
                    .append(" (").append(post.getDateTime().toLocalDate()).append(")\n");
                feed.append(post.getContent()).append("\n");
                
                // ✅ NULL SAFETY CHECKS
                int likesCount = post.getLikes() != null ? post.getLikes().size() : 0;
                int commentsCount = post.getComments() != null ? post.getComments().size() : 0;
                
                feed.append("❤️ ").append(likesCount).append(" likes | ")
                    .append("💬 ").append(commentsCount).append(" comments\n");
                feed.append("─────────────────────────────────────\n\n");
            }
            
            if (feed.length() == 0) {
                feed.append("No posts yet. Be the first to post!");
            }
            
            feedArea.setText(feed.toString());
        } catch (Exception e) {
            e.printStackTrace();
            feedArea.setText("Error loading feed: " + e.getMessage());
        }
    }
}
