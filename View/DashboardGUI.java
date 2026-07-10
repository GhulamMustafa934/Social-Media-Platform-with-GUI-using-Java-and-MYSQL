package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
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
    private String selectedImagePath = null;
    private JButton imageButton;
    private JLabel imagePreviewLabel;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(30, 30, 46);
    private final Color BG_CARD = new Color(40, 40, 60);
    private final Color BG_INPUT = new Color(50, 50, 70);
    private final Color BORDER_COLOR = new Color(60, 60, 80);
    private final Color TEXT_WHITE = Color.WHITE;
    private final Color TEXT_GRAY = new Color(180, 180, 200);
    private final Color ACCENT_BLUE = new Color(0, 150, 255);
    private final Color ACCENT_RED = new Color(255, 70, 70);
    private final Color ACCENT_GREEN = new Color(0, 200, 150);
    
    public DashboardGUI(User user) {
        this.currentUser = user;
        
        frame = new JFrame();
        frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_DARK);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // ============================================================
        // TOP PANEL: Welcome + Profile + Friends + Logout
        // ============================================================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_DARK);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(TEXT_WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(BG_DARK);
        
        JButton profileBtn = createButton("Profile");
        JButton friendsBtn = createButton("Friends");
        JButton logoutButton = createButton("Logout");
        
        rightPanel.add(profileBtn);
        rightPanel.add(friendsBtn);
        rightPanel.add(logoutButton);
        
        topPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // ============================================================
        // CREATE POST SECTION
        // ============================================================
        JPanel postPanel = new JPanel(new BorderLayout(5, 8));
        postPanel.setBackground(BG_CARD);
        postPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel shareLabel = new JLabel("Share your thoughts...");
        shareLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        shareLabel.setForeground(TEXT_GRAY);
        postPanel.add(shareLabel, BorderLayout.NORTH);
        
        JTextArea postArea = new JTextArea(3, 30);
        postArea.setBackground(BG_INPUT);
        postArea.setForeground(TEXT_WHITE);
        postArea.setCaretColor(TEXT_WHITE);
        postArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        postArea.setLineWrap(true);
        postArea.setText("What's on your mind?");
        postArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        postPanel.add(postArea, BorderLayout.CENTER);
        
        // Image Button Panel
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imagePanel.setBackground(BG_CARD);
        
        imageButton = new JButton("Add Image");
        imageButton.setBackground(BG_INPUT);
        imageButton.setForeground(TEXT_WHITE);
        imageButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        imageButton.setFocusPainted(false);
        imageButton.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        imagePanel.add(imageButton);
        
        imagePreviewLabel = new JLabel("");
        imagePreviewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        imagePreviewLabel.setForeground(ACCENT_GREEN);
        imagePanel.add(imagePreviewLabel);
        
        postPanel.add(imagePanel, BorderLayout.SOUTH);
        
        JButton postButton = new JButton("Post");
        postButton.setBackground(ACCENT_BLUE);
        postButton.setForeground(TEXT_WHITE);
        postButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        postButton.setFocusPainted(false);
        postButton.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        
        JPanel postBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        postBtnPanel.setBackground(BG_CARD);
        postBtnPanel.add(postButton);
        postPanel.add(postBtnPanel, BorderLayout.SOUTH);
        
        mainPanel.add(postPanel, BorderLayout.CENTER);
        
        // ============================================================
        // FEED SECTION
        // ============================================================
        feedPanel = new JPanel();
        feedPanel.setLayout(new GridLayout(0, 1, 0, 8));
        feedPanel.setBackground(BG_DARK);
        
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            "Feed",
            SwingConstants.LEFT,
            Font.BOLD,
            new Font("Segoe UI", Font.BOLD, 14),
            TEXT_WHITE
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new java.awt.Dimension(0, 350));
        
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        // ============================================================
        // LOAD FEED
        // ============================================================
        loadFeed();
        
        // ============================================================
        // BUTTON ACTIONS
        // ============================================================
        
        // Image Button
        imageButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                imageButton.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Pictures"));
                fileChooser.setDialogTitle("Select an image");
                
                javax.swing.filechooser.FileNameExtensionFilter filter = 
                    new javax.swing.filechooser.FileNameExtensionFilter(
                        "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter);
                
                int result = fileChooser.showOpenDialog(frame);
                if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedImagePath = selectedFile.getAbsolutePath();
                    imageButton.setText("Image Selected");
                    imageButton.setBackground(ACCENT_GREEN);
                    imagePreviewLabel.setText(selectedFile.getName());
                }
            }
        });
        
        // Post Button
        postButton.addMouseListener(new MouseListener
            () {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                postButton.setBackground(new Color(50, 180, 255));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String content = postArea.getText();
                if (content.isEmpty() || content.equals("What's on your mind?")) {
                    new Alert("Please write something!", frame);
                    return;
                }
                
                Post post;
                if (selectedImagePath != null) {
                    post = new Post(content, selectedImagePath, currentUser);
                } else {
                    post = new Post(content, currentUser);
                }
                
                CreatePost createPost = new CreatePost();
                int postId = createPost.savePost(post);
                
                if (postId > 0) {
                    new Alert("Post created!", frame);
                    postArea.setText("What's on your mind?");
                    selectedImagePath = null;
                    imageButton.setText("Add Image");
                    imageButton.setBackground(BG_INPUT);
                    imagePreviewLabel.setText("");
                    loadFeed();
                } else {
                    new Alert("Failed to create post!", frame);
                }
            }
        });
        
        // Profile Button
        profileBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                profileBtn.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new ProfileGUI(currentUser);
            }
        });
        
        // Friends Button
        friendsBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                friendsBtn.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                new FriendsGUI(currentUser);
            }
        });
        
        // Logout Button
        logoutButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new LoginGUI();
            }
        });
        
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.requestFocus();
    }
    
    // ============================================================
    // HELPER METHODS
    // ============================================================
    
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BG_CARD);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }
    
    private void loadFeed() {
        try {
            feedPanel.removeAll();
            CreatePost createPost = new CreatePost();
            LikePost likePost = new LikePost();
            CommentPost commentPost = new CommentPost();
            ArrayList<Post> posts = createPost.getAllPosts();
            
            if (posts.isEmpty()) {
                JLabel emptyLabel = new JLabel("No posts yet. Be the first to post!");
                emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                emptyLabel.setForeground(TEXT_GRAY);
                emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                feedPanel.add(emptyLabel);
            } else {
                for (Post post : posts) {
                    feedPanel.add(createPostCard(post, likePost, commentPost, createPost));
                }
            }
            
            feedPanel.revalidate();
            feedPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel createPostCard(Post post, LikePost likePost, CommentPost commentPost, CreatePost createPost) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 5));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        
        int postId = post.getId();
        
        // User name
        JLabel userLabel = new JLabel("👤 " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(TEXT_WHITE);
        card.add(userLabel, BorderLayout.NORTH);
        
        // Content
        JPanel contentPanel = new JPanel(new BorderLayout(0, 8));
        contentPanel.setBackground(BG_CARD);
        
        JLabel contentLabel = new JLabel(post.getContent());
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setForeground(TEXT_GRAY);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        
        // Image
        if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
            JLabel imageLabel = new JLabel("📷 " + new File(post.getImagePath()).getName());
            imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            imageLabel.setForeground(ACCENT_BLUE);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 5, 10));
            contentPanel.add(imageLabel, BorderLayout.CENTER);
        }
        
        card.add(contentPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        buttonPanel.setBackground(BG_CARD);
        
        // Like
        int likes = likePost.getLikesCount(postId);
        boolean liked = likePost.hasLiked(postId, currentUser.getID());
        JButton likeBtn = new JButton((liked ? "Unlike" : "Like") + " (" + likes + ")");
        likeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        likeBtn.setBackground(BG_INPUT);
        likeBtn.setForeground(TEXT_WHITE);
        likeBtn.setFocusPainted(false);
        likeBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        buttonPanel.add(likeBtn);
        
        // Comment
        int comments = commentPost.getCommentCount(postId);
        JButton commentBtn = new JButton("Comment (" + comments + ")");
        commentBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        commentBtn.setBackground(BG_INPUT);
        commentBtn.setForeground(TEXT_WHITE);
        commentBtn.setFocusPainted(false);
        commentBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        buttonPanel.add(commentBtn);
        
        // Edit & Delete
        if (post.getUser().getID() == currentUser.getID()) {
            JButton editBtn = new JButton("Edit");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            editBtn.setBackground(BG_INPUT);
            editBtn.setForeground(ACCENT_BLUE);
            editBtn.setFocusPainted(false);
            editBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
            buttonPanel.add(editBtn);
            
            JButton deleteBtn = new JButton("Delete");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            deleteBtn.setBackground(BG_INPUT);
            deleteBtn.setForeground(ACCENT_RED);
            deleteBtn.setFocusPainted(false);
            deleteBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
            buttonPanel.add(deleteBtn);
            
            editBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {
                    editBtn.setBackground(new Color(60, 60, 80));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    String newContent = javax.swing.JOptionPane.showInputDialog(frame, "Edit post:", post.getContent());
                    if (newContent != null && !newContent.trim().isEmpty()) {
                        boolean success = createPost.updatePost(postId, newContent);
                        if (success) {
                            new Alert("Post updated!", frame);
                            loadFeed();
                        }
                    }
                }
            });
            
            deleteBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {
                    deleteBtn.setBackground(new Color(60, 60, 80));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = javax.swing.JOptionPane.showConfirmDialog(frame, "Delete this post?", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                        boolean success = createPost.deletePost(postId);
                        if (success) {
                            new Alert("Post deleted!", frame);
                            loadFeed();
                        }
                    }
                }
            });
        }
        
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        // Like action
        likeBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                likeBtn.setBackground(new Color(60, 60, 80));
            }
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
            @Override public void mouseEntered(MouseEvent e) {
                commentBtn.setBackground(new Color(60, 60, 80));
            }
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