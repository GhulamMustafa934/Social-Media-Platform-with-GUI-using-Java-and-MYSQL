package View;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Controller.CommentPost;
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
        
        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(GUIConstants.lightGray);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(GUIConstants.blue);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // ✅ RIGHT PANEL: Profile + Friends + Logout buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(GUIConstants.lightGray);
        
        JButton profileButton = new JButton("👤 Profile");
        profileButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(profileButton);
        
        JButton friendsButton = new JButton("👥 Friends");
        friendsButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(friendsButton);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(logoutButton);
        
        topPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Create Post
        JPanel postPanel = new JPanel(new BorderLayout(5, 5));
        postPanel.setBackground(GUIConstants.white);
        postPanel.setBorder(BorderFactory.createTitledBorder("Create Post"));
        
        JTextArea postArea = new JTextArea(3, 30);
        postArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        postArea.setLineWrap(true);
        postArea.setText("What's on your mind?");
        
        JButton postButton = new JButton("Post");
        postButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        postPanel.add(new JScrollPane(postArea), BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.EAST);
        mainPanel.add(postPanel, BorderLayout.CENTER);
        
        // Feed
        feedPanel = new JPanel();
        feedPanel.setLayout(new GridLayout(0, 1, 5, 5));
        feedPanel.setBackground(GUIConstants.white);
        
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Feed"));
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        loadFeed();
        
        // ✅ Profile Button Action
        profileButton.addMouseListener(new MouseListener() {
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
        
        // ✅ Friends Button Action
        friendsButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                new FriendsGUI(currentUser);
            }
        });
        
        // Logout
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
        
        // Post
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
                    new Alert("Post created!", frame);
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
            CommentPost commentPost = new CommentPost();
            ArrayList<Post> posts = createPost.getAllPosts();
            
            if (posts.isEmpty()) {
                JLabel emptyLabel = new JLabel("No posts yet!");
                emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                feedPanel.add(emptyLabel);
            } else {
                for (Post post : posts) {
                    feedPanel.add(createPostCard(post, likePost, commentPost));
                }
            }
            
            feedPanel.revalidate();
            feedPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel createPostCard(Post post, LikePost likePost, CommentPost commentPost) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 5));
        card.setBackground(GUIConstants.white);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        int postId = post.getId();
        
        // User name
        JLabel userLabel = new JLabel("👤 " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(GUIConstants.black);
        card.add(userLabel, BorderLayout.NORTH);
        
        // Content
        JLabel contentLabel = new JLabel(post.getContent());
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(contentLabel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(GUIConstants.white);
        
        // Like button
        int likes = likePost.getLikesCount(postId);
        boolean liked = likePost.hasLiked(postId, currentUser.getID());
        JButton likeBtn = new JButton((liked ? "❤️ Unlike" : "🤍 Like") + " (" + likes + ")");
        likeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(likeBtn);
        
        // Comment button
        int comments = commentPost.getCommentCount(postId);
        JButton commentBtn = new JButton("💬 Comment (" + comments + ")");
        commentBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(commentBtn);
        
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        // Like action
        likeBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                if (liked) {
                    likePost.removeLike(postId, currentUser.getID());
                } else {
                    likePost.addLike(postId, currentUser.getID());
                }
                loadFeed();
            }
        });
        
        // Comment action
        commentBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                String comment = javax.swing.JOptionPane.showInputDialog(frame, "Write a comment:");
                if (comment != null && !comment.trim().isEmpty()) {
                    commentPost.addComment(postId, currentUser.getID(), comment);
                    loadFeed();
                }
            }
        });
        
        return card;
    }
}